-- 表：t_contents_record
DROP TABLE IF EXISTS t_contents_record;
CREATE TABLE t_contents_record (
  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  osc_content_id INTEGER,
  tale_content_id INTEGER NOT NULL,
  sync_time INTEGER (10),
  sync_status INTEGER (1)
);