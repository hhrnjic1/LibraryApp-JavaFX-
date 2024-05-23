BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "knjiga" (
	"id"	INTEGER,
	"naslov"	TEXT,
	"autor"	TEXT,
	"isbn"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "knjiga" VALUES (1,'Tvrdjava','Mesa Selimovic','1234-5678');
INSERT INTO "knjiga" VALUES (2,'Na Drini Cuprija','Ivo Andric','0967-5656');
INSERT INTO "knjiga" VALUES (3,'Uvod u programiranje C i C++','Vedran Ljubovic','8930-1322');
COMMIT;
