package org.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementFiller {
    void fill(PreparedStatement statement) throws SQLException, IllegalAccessException;

}
