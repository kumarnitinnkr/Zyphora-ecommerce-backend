-- ============================================================
-- Zyphora Database Setup Script
-- Run this ONCE in MySQL before starting the backend
-- ============================================================

-- 1. Create database (Spring Boot will also create it automatically
--    via createDatabaseIfNotExist=true, but this ensures it's clean)
CREATE DATABASE IF NOT EXISTS zyphora_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE zyphora_db;

-- 2. Done! Spring Boot with ddl-auto=update will create all tables
--    automatically on first startup.

-- ── Optional: seed an admin user after first run ──────────────────────────
-- (Password below is BCrypt of "admin123" — change in production!)
-- INSERT INTO users (full_name, email, password, role)
-- VALUES ('Admin', 'admin@zyphora.com',
--         '$2a$10$7EqJtq98hPqEX7fNZaFWoOe0t/.8T5e1NiJJ1.e4uXN0GpvP0hzKi',
--         'ROLE_ADMIN');
