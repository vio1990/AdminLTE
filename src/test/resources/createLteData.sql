DROP TABLE IF EXISTS LteData;
CREATE TABLE LteData (
  Id              LONG AUTO_INCREMENT,
  RenderingEngine VARCHAR(45),
  Browser         VARCHAR(45),
  Platform        VARCHAR(45),
  EngineVersion   VARCHAR(45),
  CSSGrade        VARCHAR(45),
  PRIMARY KEY (Id)
);

INSERT INTO LteData VALUES (DEFAULT , 'Webkit', 'FireFox 3.1', 'iPod', '3.1', 'A');
INSERT INTO LteData VALUES (DEFAULT , 'Gecko', 'Chrome 3.1', '60S', '3.2', 'A');
INSERT INTO LteData VALUES (DEFAULT , 'Trident', 'Safari 2.0', 'OSX.3', '3.2', 'A');