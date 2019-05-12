package db.migration;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// Ignoring the class name squid as the format
// is required here for this to work with Flyway
@SuppressWarnings("squid:S00101")
public class V2__ImportData extends BaseJavaMigration {
    private static final Logger log = LoggerFactory.getLogger(V2__ImportData.class);

    @Override
    public void migrate(Context context) throws Exception {
        final InputStream resourceAsStream = V2__ImportData.class.getClassLoader().getResourceAsStream("input_data.csv");
        final Set<Long> insertedProviders = new HashSet<>();
        if (resourceAsStream != null) {
            final CSVParser csvRecords = CSVParser.parse(resourceAsStream, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
            csvRecords.getRecords().stream().skip(1).forEach(record -> {
                Long recordId = record.getRecordNumber();
                Long providerId = Optional.ofNullable(record.get(1)).map(Long::valueOf).orElse(null);
                if (providerId == null) {
                    log.error("Provider ID for record {} is null", recordId);
                    return;
                }
                if (!insertedProviders.contains(providerId)) {
                    insertProvider(context, insertedProviders, record, providerId);
                }
                insertRecord(context, record, recordId, providerId);
            });
        }
    }

    private void insertRecord(Context context, CSVRecord record, Long recordId, Long providerId) {
        try (PreparedStatement insert = context.getConnection().prepareStatement("INSERT INTO cost_data(id,drg_definition,medical_provider_id,total_discharges,average_covered_charges,average_total_payments,average_medicare_payments) VALUES (?,?,?,?,?,?,?)")) {
            insert.setLong(1, recordId);
            insert.setString(2, record.get(1));
            insert.setLong(3, providerId);
            insert.setLong(4, Optional.ofNullable(record.get(8)).map(Long::valueOf).orElse(0L));
            insert.setBigDecimal(5, parseDollarValue(record.get(9)));
            insert.setBigDecimal(6, parseDollarValue(record.get(10)));
            insert.setBigDecimal(7, parseDollarValue(record.get(11)));
            final int result = insert.executeUpdate();
            if (result == 1) {
                log.info("Inserted data record with id {}", recordId);
            } else {
                log.error("Inserting data record with id {} produced {} results", recordId, result);
            }
        } catch (SQLException e) {
            log.error("Could not insert provider with id {}", new Object[]{providerId}, e);
        }
    }

    private void insertProvider(Context context, Set<Long> insertedProviders, CSVRecord record, Long providerId) {
        try (PreparedStatement insert = context.getConnection().prepareStatement("INSERT INTO medical_provider(id,name,street_address,city,state,zip_code,referral_region_description) VALUES (?,?,?,?,?,?,?)")) {
            insert.setLong(1, providerId);
            insert.setString(2, record.get(2));
            insert.setString(3, record.get(3));
            insert.setString(4, record.get(4));
            insert.setString(5, record.get(5));
            insert.setLong(6, Optional.ofNullable(record.get(6)).map(Long::valueOf).orElse(-1L));
            insert.setString(7, record.get(7));
            final int result = insert.executeUpdate();
            if (result == 1) {
                log.debug("Inserted provider with id {}", providerId);
                insertedProviders.add(providerId);
            } else {
                log.error("Inserting provider with id {} produced {} results", providerId, result);
            }
        } catch (SQLException e) {
            log.error("Could not insert provider with id {}", new Object[]{providerId}, e);
        }
    }

    private static BigDecimal parseDollarValue(String value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value.replace("$", ""));
    }
}
