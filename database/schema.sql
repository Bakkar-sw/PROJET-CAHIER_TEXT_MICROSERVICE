-- ============================================
-- Script de Création de la Base de Données
-- Cahier Texte TDSI - Architecture Microservices
-- ============================================

-- 1. Créer la base de données (si elle n'existe pas)
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'cahier_texte_db_micro')
BEGIN
    CREATE DATABASE cahier_texte_db_micro;
END
GO

USE cahier_texte_db_micro;
GO

-- ============================================
-- 2. Table USERS (Auth Service + User Service)
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    CREATE TABLE users (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        username VARCHAR(50) NOT NULL UNIQUE,
        email VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,  -- Hash SHA-256
        prenom VARCHAR(50) NOT NULL,
        nom VARCHAR(50) NOT NULL,
        role VARCHAR(50) NOT NULL,  -- RESPONSABLE_FORMATION, RESPONSABLE_CLASSE, PROFESSEUR, ETUDIANT
        classe VARCHAR(20),  -- CI_M1, CI_M2, MCS_M1, MCS_M2
        actif BIT NOT NULL DEFAULT 1,
        date_creation DATETIME NOT NULL DEFAULT GETDATE(),
        date_modification DATETIME NOT NULL DEFAULT GETDATE()
    );
    
    PRINT '✅ Table users créée';
END
ELSE
    PRINT '⚠️  Table users existe déjà';
GO

-- ============================================
-- 3. Table MATIERES (Cours Service)
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'matieres')
BEGIN
    CREATE TABLE matieres (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        nom VARCHAR(100) NOT NULL,
        code VARCHAR(20) NOT NULL UNIQUE,
        volume_horaire INT NOT NULL,  -- Volume horaire total
        volume_realise INT NOT NULL DEFAULT 0,  -- Volume horaire réalisé
        professeur_id BIGINT NOT NULL,
        classe VARCHAR(20) NOT NULL,
        actif BIT NOT NULL DEFAULT 1,
        date_creation DATETIME NOT NULL DEFAULT GETDATE(),
        date_modification DATETIME NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (professeur_id) REFERENCES users(id)
    );
    
    PRINT '✅ Table matieres créée';
END
ELSE
    PRINT '⚠️  Table matieres existe déjà';
GO

-- ============================================
-- 4. Table COURS (Cours Service)
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'cours')
BEGIN
    CREATE TABLE cours (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        matiere_id BIGINT NOT NULL,
        professeur_id BIGINT NOT NULL,
        classe VARCHAR(20) NOT NULL,
        date_cours DATE NOT NULL,
        heure_debut TIME NOT NULL,
        heure_fin TIME NOT NULL,
        salle VARCHAR(50),
        cahier_texte TEXT,  -- Contenu pédagogique
        status VARCHAR(20) NOT NULL DEFAULT 'PLANIFIE',  -- PLANIFIE, VALIDE, TERMINE, ANNULE
        valide_par_prof BIT NOT NULL DEFAULT 0,
        date_validation DATETIME,
        date_creation DATETIME NOT NULL DEFAULT GETDATE(),
        date_modification DATETIME NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (matiere_id) REFERENCES matieres(id),
        FOREIGN KEY (professeur_id) REFERENCES users(id)
    );
    
    CREATE INDEX idx_cours_classe ON cours(classe);
    CREATE INDEX idx_cours_professeur ON cours(professeur_id);
    CREATE INDEX idx_cours_date ON cours(date_cours);
    
    PRINT '✅ Table cours créée';
END
ELSE
    PRINT '⚠️  Table cours existe déjà';
GO

-- ============================================
-- 5. Table PRESENCES (Presence Service)
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'presences')
BEGIN
    CREATE TABLE presences (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        cours_id BIGINT NOT NULL,
        etudiant_id BIGINT NOT NULL,
        status VARCHAR(20) NOT NULL,  -- PRESENT, ABSENT, RETARD
        remarque VARCHAR(255),
        date_creation DATETIME NOT NULL DEFAULT GETDATE(),
        date_modification DATETIME NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (cours_id) REFERENCES cours(id),
        FOREIGN KEY (etudiant_id) REFERENCES users(id),
        UNIQUE (cours_id, etudiant_id)  -- Un étudiant ne peut avoir qu'une seule présence par cours
    );
    
    CREATE INDEX idx_presences_cours ON presences(cours_id);
    CREATE INDEX idx_presences_etudiant ON presences(etudiant_id);
    
    PRINT '✅ Table presences créée';
END
ELSE
    PRINT '⚠️  Table presences existe déjà';
GO

-- ============================================
-- 6. Table JUSTIFICATIFS (Justificatif Service)
-- ============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'justificatifs')
BEGIN
    CREATE TABLE justificatifs (
        id BIGINT PRIMARY KEY IDENTITY(1,1),
        etudiant_id BIGINT NOT NULL,
        cours_id BIGINT NOT NULL,
        motif TEXT NOT NULL,
        fichier VARCHAR(255),  -- Chemin vers le fichier justificatif
        status VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',  -- EN_ATTENTE, ACCEPTE, REFUSE
        date_soumission DATETIME NOT NULL DEFAULT GETDATE(),
        date_traitement DATETIME,
        traite_par BIGINT,  -- ID du responsable qui a traité
        commentaire_traitement TEXT,
        FOREIGN KEY (etudiant_id) REFERENCES users(id),
        FOREIGN KEY (cours_id) REFERENCES cours(id),
        FOREIGN KEY (traite_par) REFERENCES users(id)
    );
    
    CREATE INDEX idx_justificatifs_etudiant ON justificatifs(etudiant_id);
    CREATE INDEX idx_justificatifs_status ON justificatifs(status);
    
    PRINT '✅ Table justificatifs créée';
END
ELSE
    PRINT '⚠️  Table justificatifs existe déjà';
GO

-- ============================================
-- 7. Données de Test
-- ============================================

-- Utilisateurs de test
-- Mot de passe pour tous : "password123" (hash SHA-256)
IF NOT EXISTS (SELECT * FROM users WHERE username = 'rf.diop')
BEGIN
    INSERT INTO users (username, email, password, prenom, nom, role, classe, actif)
    VALUES 
    ('rf.diop', 'rf.diop@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Fatou', 'Diop', 'RESPONSABLE_FORMATION', NULL, 1),
    ('rc.fall', 'rc.fall@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Moussa', 'Fall', 'RESPONSABLE_CLASSE', 'CI_M1', 1),
    ('prof.samb', 'prof.samb@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Amadou', 'Samb', 'PROFESSEUR', NULL, 1),
    ('prof.sow', 'prof.sow@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Aissatou', 'Sow', 'PROFESSEUR', NULL, 1),
    ('etud.ndiaye', 'etud.ndiaye@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Fatou', 'Ndiaye', 'ETUDIANT', 'CI_M1', 1),
    ('etud.ba', 'etud.ba@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Abdou', 'Ba', 'ETUDIANT', 'CI_M1', 1),
    ('etud.diallo', 'etud.diallo@tdsi.sn', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'Mariama', 'Diallo', 'ETUDIANT', 'CI_M2', 1);
    
    PRINT '✅ Utilisateurs de test créés';
END
ELSE
    PRINT '⚠️  Utilisateurs de test existent déjà';
GO

-- Matières de test
IF NOT EXISTS (SELECT * FROM matieres WHERE code = 'CYB101')
BEGIN
    INSERT INTO matieres (nom, code, volume_horaire, volume_realise, professeur_id, classe, actif)
    VALUES 
    ('Introduction à la Cybersécurité', 'CYB101', 40, 0, 3, 'CI_M1', 1),
    ('Cryptographie Avancée', 'CYB201', 35, 0, 3, 'CI_M2', 1),
    ('Investigation Numérique', 'INV101', 45, 0, 4, 'CI_M1', 1),
    ('Management de la Sécurité', 'MGT101', 30, 0, 4, 'MCS_M1', 1);
    
    PRINT '✅ Matières de test créées';
END
ELSE
    PRINT '⚠️  Matières de test existent déjà';
GO

-- Cours de test
IF NOT EXISTS (SELECT * FROM cours WHERE id = 1)
BEGIN
    INSERT INTO cours (matiere_id, professeur_id, classe, date_cours, heure_debut, heure_fin, salle, cahier_texte, status, valide_par_prof)
    VALUES 
    (1, 3, 'CI_M1', '2026-02-10', '08:00', '10:00', 'Salle A1', 'Introduction aux concepts de base de la cybersécurité', 'PLANIFIE', 0),
    (1, 3, 'CI_M1', '2026-02-12', '10:00', '12:00', 'Salle A2', NULL, 'PLANIFIE', 0),
    (3, 4, 'CI_M1', '2026-02-11', '14:00', '17:00', 'Lab Info', 'TP sur les outils d''investigation', 'VALIDE', 1);
    
    PRINT '✅ Cours de test créés';
END
ELSE
    PRINT '⚠️  Cours de test existent déjà';
GO

-- ============================================
-- 8. Vues Utiles (Optionnel)
-- ============================================

-- Vue pour les statistiques de présence par étudiant
IF NOT EXISTS (SELECT * FROM sys.views WHERE name = 'v_stats_presence_etudiant')
BEGIN
    EXEC('
    CREATE VIEW v_stats_presence_etudiant AS
    SELECT 
        u.id AS etudiant_id,
        u.username,
        u.prenom,
        u.nom,
        u.classe,
        COUNT(p.id) AS total_seances,
        SUM(CASE WHEN p.status = ''PRESENT'' THEN 1 ELSE 0 END) AS presences,
        SUM(CASE WHEN p.status = ''ABSENT'' THEN 1 ELSE 0 END) AS absences,
        SUM(CASE WHEN p.status = ''RETARD'' THEN 1 ELSE 0 END) AS retards,
        CAST(SUM(CASE WHEN p.status = ''PRESENT'' THEN 1 ELSE 0 END) * 100.0 / NULLIF(COUNT(p.id), 0) AS DECIMAL(5,2)) AS taux_presence
    FROM users u
    LEFT JOIN presences p ON u.id = p.etudiant_id
    WHERE u.role = ''ETUDIANT''
    GROUP BY u.id, u.username, u.prenom, u.nom, u.classe
    ');
    
    PRINT '✅ Vue v_stats_presence_etudiant créée';
END
GO

-- Vue pour les matières avec alertes (< 12h restantes)
IF NOT EXISTS (SELECT * FROM sys.views WHERE name = 'v_matieres_alertes')
BEGIN
    EXEC('
    CREATE VIEW v_matieres_alertes AS
    SELECT 
        m.id,
        m.nom,
        m.code,
        m.volume_horaire,
        m.volume_realise,
        (m.volume_horaire - m.volume_realise) AS heures_restantes,
        m.classe,
        u.prenom + '' '' + u.nom AS professeur
    FROM matieres m
    JOIN users u ON m.professeur_id = u.id
    WHERE (m.volume_horaire - m.volume_realise) < 12 
      AND (m.volume_horaire - m.volume_realise) > 0
      AND m.actif = 1
    ');
    
    PRINT '✅ Vue v_matieres_alertes créée';
END
GO

-- ============================================
-- Script terminé
-- ============================================
PRINT '';
PRINT '============================================';
PRINT '✅ SCRIPT TERMINÉ AVEC SUCCÈS !';
PRINT '============================================';
PRINT '';
PRINT '📊 Tables créées :';
PRINT '  - users';
PRINT '  - matieres';
PRINT '  - cours';
PRINT '  - presences';
PRINT '  - justificatifs';
PRINT '';
PRINT '👥 Comptes de test (mot de passe : password123) :';
PRINT '  - rf.diop (Responsable Formation)';
PRINT '  - rc.fall (Responsable Classe)';
PRINT '  - prof.samb (Professeur)';
PRINT '  - etud.ndiaye (Étudiant)';
PRINT '';
PRINT '🚀 Vous pouvez maintenant démarrer les microservices !';
PRINT '============================================';
GO
