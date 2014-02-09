package org.hibernate.dialect.pagination;

import org.hibernate.engine.spi.RowSelection;

/**
 * An implementation of {@link org.hibernate.dialect.pagination.LimitHandler}
 * for the {@link org.hibernate.dialect.SQLiteDialect} based on
 * {@link org.hibernate.dialect.pagination.CUBRIDLimitHandler}.
 */
public class SQLiteLimitHandler extends AbstractLimitHandler {

  /**
   * {@inheritDoc}
   */
  public SQLiteLimitHandler(String sql, RowSelection selection) {
    super(sql, selection);
  }

  @Override
  public boolean supportsLimit() {
    return true;
  }

  @Override
  public String getProcessedSql() {
    String result;

    if (LimitHelper.useLimit(this, this.selection)) {
      final boolean useLimitOffset = LimitHelper.hasFirstRow(this.selection);
      result = this.sql + (useLimitOffset ? " limit ? offset ?" : " limit ?");
    } else {
      result = this.sql;
    }

    return result;
  }
}