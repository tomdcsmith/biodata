package org.opencb.biodata.tools.alignment.filters;

import htsjdk.samtools.SAMRecord;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by imedina on 11/11/16.
 */
public class SamRecordFilters extends AlignmentFilters<SAMRecord> {

    public SamRecordFilters() {
    }

    public SamRecordFilters(List<Predicate<SAMRecord>> filters) {
        this.filters = filters;
    }

    public static AlignmentFilters<SAMRecord> create() {
        return new SamRecordFilters();
    }

    @Override
    public AlignmentFilters<SAMRecord> addMappingQualityFilter(int mappingQuality) {
        filters.add(samRecord -> samRecord.getMappingQuality() > mappingQuality);
        return this;
    }

    @Override
    public AlignmentFilters<SAMRecord> addProperlyPairedFilter() {
        filters.add(samRecord -> samRecord.getProperPairFlag());
        return this;
    }

    @Override
    public AlignmentFilters<SAMRecord> addUnmappedFilter() {
        filters.add(samRecord -> samRecord.getReadUnmappedFlag());
        return this;
    }
}
