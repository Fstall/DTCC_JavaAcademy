package com.rstech.wordwatch.dao.util;

import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;

public interface ReportAndReportEntryMapper { 
        public static class ReportWithReportEntry {
                public WordReport      report;
                public WordReportEntry entry;
        }
}

 