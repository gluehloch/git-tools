# Document Managemenet for me DMFM

* Datenversionierung mit GIT
* Apache Lucene zur Indexierung und Suche
* Apache PDF Box / Untersuchen und erstellen von PDF Dokumenten
* Web-Seite für das Hochladen von Dokumenten
* Web-Seite für das Suchen nach Dokumenten
* Handy App für das Scannen von Dokumenten
* Ablage von Rechnungen. Status offen, bezahlt, Rückerstattung,...

## Markdown
* Markdown Integration in meine Web-Seite
  * Java Wiki mit verschiedenen Texten als GIT Repository.
  * ...  
* Wie wird die Seitenstruktur definiert?`Bzw. wie die einzelnen Dateien einbinden?

## Architektur
* GIT Repository mit Lucene Index
* DB mit Labels/Vorgangsnr auf Dokumente
* Vorgang: Titel, Beschreibung, Status, Dokumente angehängt.
  * Status: In Bearbeitung, Abgeschlossen,...
  * Liegt der Vorgang in der DB oder als Datei im GIT Repo?
    * Datei: JSON Struktur, Referenzen auf Dokumente
    * SQL, relational: Keine Versionierung wie in GIT. Müsste selbst
      implementiert werden.