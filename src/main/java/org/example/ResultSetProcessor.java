package org.example;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetProcessor<T> {
   List<T> process(ResultSet rs);
}
