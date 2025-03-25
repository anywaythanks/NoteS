package com.notes.repository.impl;

import com.notes.models.Case;
import com.notes.models.CaseSlot;
import com.notes.models.Item;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JdbcPostgreSQLCaseSlot {
    public Work createTempTable(String tempTableName) {
        return connection -> {
            try (PreparedStatement tempTable = connection.prepareStatement(
                    "CREATE TEMP TABLE " + tempTableName + " (LIKE CASE_SLOTS INCLUDING DEFAULTS);"
            )) {
                tempTable.executeUpdate();
            }
        };
    }

    public Work merge(String tempTableName, Case caseOwner) {
        return connection -> {
            try (PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO CASE_SLOTS SELECT * FROM " + tempTableName + " WHERE CASE_ID = ?;"
            )) {
                insert.setLong(1, caseOwner.getId());
                insert.executeUpdate();
            }
        };
    }

    public Work insertSlots(String tempTableName, Case caseOwner, Collection<CaseSlot<Item>> slots) {
        return connection -> {
            for (CaseSlot<Item> slot : slots) {
                try (PreparedStatement sequence = connection.prepareStatement(
                        "SELECT NEXTVAL('slot_id_seq');"
                )) {
                    ResultSet result = sequence.executeQuery();
                    result.next();
                    long id = result.getLong(1);
                    try (PreparedStatement insert = connection.prepareStatement(
                            "INSERT INTO " + tempTableName + " (ID, CASE_ID, ITEM_ID, NAME, WIN_RATE, QUANTITY_ITEM) " +
                                    "VALUES (?, ?, ?, ?, ?, ?);"
                    )) {
                        insert.setLong(1, id);
                        insert.setLong(2, caseOwner.getId());
                        insert.setLong(3, slot.getItem().getId());
                        insert.setString(4, slot.getName().getName());
                        insert.setBigDecimal(5, slot.getWinRate());
                        insert.setLong(6, slot.getQuantityItem());
                        insert.executeUpdate();
                    }
                }
            }
        };
    }

    public Work dropTempTable(String tempTableName) {
        return connection -> {
            try (PreparedStatement tempTable = connection.prepareStatement(
                    "DROP TABLE " + tempTableName + ";")
            ) {
                tempTable.executeUpdate();
            }
        };
    }
}
