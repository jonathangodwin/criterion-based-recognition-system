-- ==========================================
-- SCHEMA : QUESACO DATABASE
-- ==========================================

-- TABLE 1 : Domain
CREATE TABLE domain (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- ==========================================

-- TABLE 2 : Typology
CREATE TABLE typology (
    id_typology SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    id_domain INT,
    FOREIGN KEY (id_domain) REFERENCES domain(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ==========================================

-- TABLE 3 : Typology_Attribute
CREATE TABLE typology_attribute (
    id_attribute SERIAL PRIMARY KEY,
    id_typology INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    valeur VARCHAR(255),
    type VARCHAR(50) CHECK (type IN ('numerical', 'categorical', 'boolean')),
    FOREIGN KEY (id_typology) REFERENCES typology(id_typology)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ==========================================

-- TABLE 4 : Observation
CREATE TABLE observation (
    id_observation SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    id_domain INT,
    FOREIGN KEY (id_domain) REFERENCES domain(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ==========================================

-- TABLE 5 : Observation_Attribute
CREATE TABLE observation_attribute (
    id_attribute SERIAL PRIMARY KEY,
    id_observation INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    valeur VARCHAR(255),
    type VARCHAR(50) CHECK (type IN ('numerical', 'categorical', 'boolean')),
    FOREIGN KEY (id_observation) REFERENCES observation(id_observation)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ==========================================

-- INDEXES pour accélérer les recherches
CREATE INDEX idx_typology_domain ON typology(id_domain);
CREATE INDEX idx_observation_domain ON observation(id_domain);
CREATE INDEX idx_ta_typology ON typology_attribute(id_typology);
CREATE INDEX idx_oa_observation ON observation_attribute(id_observation);

-- ==========================================
-- EXEMPLES D'INSERTION INITIALE
-- ==========================================

INSERT INTO domain (name) VALUES ('Botanique');
INSERT INTO domain (name) VALUES ('Zoologie');

INSERT INTO typology (name, description, id_domain)
VALUES ('Arbre', 'Typologie des arbres feuillus et résineux', 1);

INSERT INTO typology_attribute (id_typology, name, valeur, type)
VALUES (1, 'Hauteur moyenne', '20', 'numerical'),
       (1, 'Type de feuille', 'persistante', 'categorical'),
       (1, 'Fleurit', 'true', 'boolean');

INSERT INTO observation (name, description, id_domain)
VALUES ('Observation Chêne', 'Chêne observé en forêt', 1);

INSERT INTO observation_attribute (id_observation, name, valeur, type)
VALUES (1, 'Hauteur mesurée', '22', 'numerical'),
       (1, 'Type de feuille observé', 'persistante', 'categorical'),
       (1, 'Présence de fleurs', 'false', 'boolean');
