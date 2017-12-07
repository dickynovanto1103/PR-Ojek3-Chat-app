#!/bin/bash

echo "apply_sqls.sh by nieltg, Daniel" 1>&2
echo 1>&2

MIGRATION_DIR="$1"
shift

if ! [ -d "${MIGRATION_DIR}" ]
then
  echo "Usage: $0 MIGRATION_DIR MYSQL_PARAMS..." 1>&2
  exit 1
fi

# Generate script.

echo "Applying SQLs... " 1>&2

print_sqls() {
  for sql in "${MIGRATION_DIR}"/*.sql
  do
    echo "${sql}" 1>&2
    echo "source ${sql}"
  done
}

print_sqls | mysql "$@"

echo "done." 1>&2
echo 1>&2
