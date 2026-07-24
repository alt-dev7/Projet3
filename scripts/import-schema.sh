#!/usr/bin/env bash
set -e
mysql -u chatop_user -p chatop_db < ressources/sql/script.sql
