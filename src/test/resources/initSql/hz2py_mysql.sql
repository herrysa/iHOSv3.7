SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS t_hz2py;
CREATE TABLE t_hz2py (
  PY char(1) CHARACTER SET utf8 NOT NULL,
  HZ char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (PY)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

INSERT INTO t_hz2py VALUES ('A', '骜');
INSERT INTO t_hz2py VALUES ('B', '簿');
INSERT INTO t_hz2py VALUES ('C', '错');
INSERT INTO t_hz2py VALUES ('D', '鵽');
INSERT INTO t_hz2py VALUES ('E', '樲');
INSERT INTO t_hz2py VALUES ('F', '鳆');
INSERT INTO t_hz2py VALUES ('G', '腂');
INSERT INTO t_hz2py VALUES ('H', '夻');
INSERT INTO t_hz2py VALUES ('J', '攈');
INSERT INTO t_hz2py VALUES ('K', '穒');
INSERT INTO t_hz2py VALUES ('L', '鱳');
INSERT INTO t_hz2py VALUES ('M', '旀');
INSERT INTO t_hz2py VALUES ('N', '桛');
INSERT INTO t_hz2py VALUES ('O', '沤');
INSERT INTO t_hz2py VALUES ('P', '曝');
INSERT INTO t_hz2py VALUES ('Q', '囕');
INSERT INTO t_hz2py VALUES ('R', '鶸');
INSERT INTO t_hz2py VALUES ('S', '蜶');
INSERT INTO t_hz2py VALUES ('T', '箨');
INSERT INTO t_hz2py VALUES ('W', '鹜');
INSERT INTO t_hz2py VALUES ('X', '鑂');
INSERT INTO t_hz2py VALUES ('Y', '韵');
INSERT INTO t_hz2py VALUES ('Z', '咗');

