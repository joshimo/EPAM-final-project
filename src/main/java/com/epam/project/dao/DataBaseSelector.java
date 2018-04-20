package com.epam.project.dao;

public enum DataBaseSelector {
    MY_SQL {
        @Override
        public String toString() {
            return "MySQL";
        }
    },
    MS_SQL {
        @Override
        public String toString() {
            return "Microsoft SQL Server";
        }
    },
    ORACLE {
        @Override
        public String toString() {
            return "Oracle Database";
        }
    },
    POSTGRESS {
        @Override
        public String toString() {
            return "PostgreSQL";
        }
    }
}
