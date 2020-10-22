DROP DATABASE IF EXISTS superHeroSightings;
CREATE DATABASE superHeroSightings;

USE superHeroSightings;

CREATE TABLE supe (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    firstName       VARCHAR(30)    	NOT NULL,
    lastName        VARCHAR(50)	   	NOT NULL,
	description		TEXT			NOT NULL,
	superPower		VARCHAR(50) 	NOT NULL
);

CREATE TABLE organization (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    name    		VARCHAR(60)    	NOT NULL,
    description    	TEXT	   		NOT NULL,
	phoneNumber		VARCHAR(20)		NOT NULL,
	email			VARCHAR(50) 	NOT NULL,
    address     	VARCHAR(60)    	NOT NULL,
    postCode    	VARCHAR(10)	   	NOT NULL,
	city			VARCHAR(70)		NOT NULL,
	country			VARCHAR(70) 	NOT NULL
);

CREATE TABLE supeOrganization (
    supeID 			INT,
    organizationID 	INT,
    CONSTRAINT pk_supeOrganization 
    	PRIMARY KEY (supeID, organizationID),
    CONSTRAINT fk_supeOrganization_supe
    	FOREIGN KEY (supeID)
    	REFERENCES supe(id),
    CONSTRAINT fk_supeOrganization_organization
    	FOREIGN KEY (organizationID)
    	REFERENCES organization(id)
);

CREATE TABLE location (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    name     		VARCHAR(80)    	NOT NULL,
    description    	TEXT	   		NOT NULL,
	latitude		DECIMAL(6,4)	NOT NULL,
	longitude		DECIMAL(7,4) 	NOT NULL,
    address     	VARCHAR(60)    	NOT NULL,
    postCode    	VARCHAR(10)	   	NOT NULL,
	city			VARCHAR(70)		NOT NULL,
	country			VARCHAR(70) 	NOT NULL
);

CREATE TABLE sighting(
    id 					INT 		PRIMARY KEY AUTO_INCREMENT,
    sightingDate		DATETIME	NOT NULL,
	locationID			INT			NOT NULL,
    CONSTRAINT fk_sight_location
    	FOREIGN KEY (locationID)
    	REFERENCES location(id)
);

CREATE TABLE supeSighting (
    supeID 			INT,
    sightingID	 	INT,
    CONSTRAINT pk_supeSighting 
    	PRIMARY KEY (supeID, sightingID),
    CONSTRAINT fk_supeSighting_supe
    	FOREIGN KEY (supeID)
    	REFERENCES supe(id),
    CONSTRAINT fk_supeSighting_sighting
    	FOREIGN KEY (sightingID)
    	REFERENCES sighting(id)
);

DROP DATABASE IF EXISTS superHeroSightingsTest;
CREATE DATABASE superHeroSightingsTest;

USE superHeroSightingsTest;

CREATE TABLE supe (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    firstName       VARCHAR(30)    	NOT NULL,
    lastName        VARCHAR(50)	   	NOT NULL,
	description		TEXT			NOT NULL,
	superPower		VARCHAR(50) 	NOT NULL
);

CREATE TABLE organization (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    name    		VARCHAR(80)    	NOT NULL,
    description    	TEXT	   		NOT NULL,
	phoneNumber		VARCHAR(20)		NOT NULL,
	email			VARCHAR(50) 	NOT NULL,
    address     	VARCHAR(60)    	NOT NULL,
    postCode    	VARCHAR(10)	   	NOT NULL,
	city			VARCHAR(70)		NOT NULL,
	country			VARCHAR(70) 	NOT NULL
);

CREATE TABLE supeOrganization (
    supeID 			INT,
    organizationID 	INT,
    CONSTRAINT pk_supeOrganization 
    	PRIMARY KEY (supeID, organizationID),
    CONSTRAINT fk_supeOrganization_supe
    	FOREIGN KEY (supeID)
    	REFERENCES supe(id),
    CONSTRAINT fk_supeOrganization_organization
    	FOREIGN KEY (organizationID)
    	REFERENCES organization(id)
);

CREATE TABLE location (
    id 				INT 			PRIMARY KEY AUTO_INCREMENT,
    name     		VARCHAR(80)    	NOT NULL,
    description    	TEXT	   		NOT NULL,
	latitude		DECIMAL(6,4)	NOT NULL,
	longitude		DECIMAL(7,4) 	NOT NULL,
    address     	VARCHAR(60)    	NOT NULL,
    postCode    	VARCHAR(10)	   	NOT NULL,
	city			VARCHAR(70)		NOT NULL,
	country			VARCHAR(70) 	NOT NULL
);

CREATE TABLE sighting(
    id 		INT 		PRIMARY KEY AUTO_INCREMENT,
    sightingDate		DATETIME	NOT NULL,
	locationID			INT			NOT NULL,
    CONSTRAINT fk_sight_location
    	FOREIGN KEY (locationID)
    	REFERENCES location(id)
);

CREATE TABLE supeSighting (
    supeID 			INT,
    sightingID	 	INT,
    CONSTRAINT pk_supeSighting 
    	PRIMARY KEY (supeID, sightingID),
    CONSTRAINT fk_supeSighting_supe
    	FOREIGN KEY (supeID)
    	REFERENCES supe(id),
    CONSTRAINT fk_supeSighting_sighting
    	FOREIGN KEY (sightingID)
    	REFERENCES sighting(id)
);