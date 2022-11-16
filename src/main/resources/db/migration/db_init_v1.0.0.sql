-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               PostgreSQL 11.4, compiled by Visual C++ build 1914, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table public.comments
CREATE TABLE IF NOT EXISTS "comments" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''comments_id_seq''::regclass)',
	"body" VARCHAR(255) NULL DEFAULT NULL,
	"email" VARCHAR(255) NULL DEFAULT NULL,
	"name" VARCHAR(255) NULL DEFAULT NULL,
	"post_id" BIGINT NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkh4c7lvsc298whoyd4w9ta25cr" FOREIGN KEY ("post_id") REFERENCES "posts" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.flyway_schema_history
CREATE TABLE IF NOT EXISTS "flyway_schema_history" (
	"installed_rank" INTEGER NOT NULL,
	"version" VARCHAR(50) NULL DEFAULT NULL,
	"description" VARCHAR(200) NOT NULL,
	"type" VARCHAR(20) NOT NULL,
	"script" VARCHAR(1000) NOT NULL,
	"checksum" INTEGER NULL DEFAULT NULL,
	"installed_by" VARCHAR(100) NOT NULL,
	"installed_on" TIMESTAMP NOT NULL DEFAULT 'now()',
	"execution_time" INTEGER NOT NULL,
	"success" BOOLEAN NOT NULL,
	PRIMARY KEY ("installed_rank"),
	INDEX "flyway_schema_history_s_idx" ("success")
);

-- Data exporting was unselected.

-- Dumping structure for table public.jwt_blocklist_tokens
CREATE TABLE IF NOT EXISTS "jwt_blocklist_tokens" (
	"token" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("token")
);

-- Data exporting was unselected.

-- Dumping structure for table public.posts
CREATE TABLE IF NOT EXISTS "posts" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''posts_id_seq''::regclass)',
	"content" VARCHAR(255) NULL DEFAULT NULL,
	"description" VARCHAR(255) NULL DEFAULT NULL,
	"title" VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table public.roles
CREATE TABLE IF NOT EXISTS "roles" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''roles_id_seq''::regclass)',
	"name" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE INDEX "uk_ofx66keruapi6vyqpv6f2or37" ("name")
);

-- Data exporting was unselected.

-- Dumping structure for table public.users
CREATE TABLE IF NOT EXISTS "users" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''users_id_seq''::regclass)',
	"email" VARCHAR(50) NOT NULL,
	"first_name" VARCHAR(255) NULL DEFAULT NULL,
	"last_name" VARCHAR(255) NULL DEFAULT NULL,
	"password" VARCHAR(255) NOT NULL,
	"username" VARCHAR(50) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE INDEX "uk_6dotkott2kjsp8vw4d0m25fb7" ("email"),
	UNIQUE INDEX "uk_r43af9ap4edm43mmtq01oddj6" ("username")
);

-- Data exporting was unselected.

-- Dumping structure for table public.user_roles
CREATE TABLE IF NOT EXISTS "user_roles" (
	"user_id" BIGINT NOT NULL,
	"role_id" BIGINT NOT NULL,
	PRIMARY KEY ("user_id", "role_id"),
	CONSTRAINT "fkh8ciramu9cc9q3qcqiv4ue8a6" FOREIGN KEY ("role_id") REFERENCES "roles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkhfh9dx7w3ubf1co1vdev94g3f" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
