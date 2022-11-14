package com.tsa.dbclient.domain.interfaces;

import java.util.List;

public interface ResultWriter {
    void write(String query, List<?> list);
}
