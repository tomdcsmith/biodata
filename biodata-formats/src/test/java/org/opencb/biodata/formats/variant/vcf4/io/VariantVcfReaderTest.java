package org.opencb.biodata.formats.variant.vcf4.io;

import org.junit.Test;
import org.opencb.biodata.formats.variant.io.VariantReader;
import org.opencb.biodata.formats.variant.vcf4.VcfAlternateHeader;
import org.opencb.biodata.formats.variant.vcf4.VcfFilterHeader;
import org.opencb.biodata.formats.variant.vcf4.VcfFormatHeader;
import org.opencb.biodata.formats.variant.vcf4.VcfInfoHeader;
import org.opencb.biodata.models.variant.Variant;
import org.opencb.biodata.models.variant.VariantSource;
import org.opencb.biodata.models.variant.VariantVcfEVSFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VariantVcfReaderTest {

    @Test
    public void readFile() {
        String inputFile = getClass().getResource("/variant-test-file.vcf.gz").getFile();
        VariantSource source = new VariantSource(inputFile, "test", "test", "Test file");
    
        VariantReader reader = new VariantVcfReader(source, inputFile);

        List<Variant> variants;

        assertTrue(reader.open());
        assertTrue(reader.pre());

        int count = 0;
        while ((variants = reader.read()) != null) {
            count += variants.size();
        }

        assertEquals(count, 999);

        assertTrue(reader.post());
        assertTrue(reader.close());
    }

    @Test
    public void readFileVersion4_1() {
        String inputFile = getClass().getResource("/variant-test-file-4.1.vcf.gz").getFile();
        VariantSource source = new VariantSource(inputFile, "test", "test", "Test file");

        VariantReader reader = new VariantVcfReader(source, inputFile);

        List<Variant> variants;

        assertTrue(reader.open());
        assertTrue(reader.pre());

        int count = 0;
        while ((variants = reader.read()) != null) {
            count += variants.size();
        }

        assertEquals(753, count);

        assertTrue(reader.post());
        assertTrue(reader.close());
    }

    @Test
    public void testHeaderVersion4_1() {
        String inputFile = getClass().getResource("/variant-test-file-4.1.vcf.gz").getFile();
        VariantSource source = new VariantSource(inputFile, "test", "test", "Test file");

        VariantReader reader = new VariantVcfReader(source, inputFile);

        assertTrue(reader.open());
        assertTrue(reader.pre());
        assertTrue(reader.post());
        assertTrue(reader.close());

        checkTypeAndAmount(source, "contig", String.class, 86);
        checkTypeAndAmount(source, "FILTER", VcfFilterHeader.class, 1);
        checkTypeAndAmount(source, "ALT", VcfAlternateHeader.class, 133);
        checkTypeAndAmount(source, "FORMAT", VcfFormatHeader.class, 1);
        checkTypeAndAmount(source, "INFO", VcfInfoHeader.class, 27);
    }

    private <T> void checkTypeAndAmount(VariantSource source, String key, Class<T> clazz, int expectedAmount) {
        assertNotNull(source.getMetadata().get(key));
        assertFalse(((Collection)source.getMetadata().get(key)).isEmpty());
        assertTrue(clazz.isInstance(((Collection)source.getMetadata().get(key)).iterator().next()));
        assertEquals(expectedAmount, ((Collection)source.getMetadata().get(key)).size());
    }

    /**
     * Use the reader to get the header of a VCF, and then manually compare it with the actual header. Use a set to
     * sort the lines, because the reader will change the order: first the unrecognized metadata lines, then
     * ALT, FILTER, INFO, FORMAT, and last the header line (#CHROM...).
     * @throws Exception
     */
    @Test
    public void testVcf4ToString() throws Exception {
        String inputFile = getClass().getResource("/variant-test-file-4.1.vcf.gz").getFile();
        VariantSource source = new VariantSource(inputFile, "test", "test", "Test file");

        VariantReader reader = new VariantVcfReader(source, inputFile);

        assertTrue(reader.open());
        assertTrue(reader.pre());
        assertTrue(reader.post());
        assertTrue(reader.close());

        Set<String> actualLines = new TreeSet<>();
        String[] split = reader.getHeader().split("\n");
        Collections.addAll(actualLines, split);

        // get the expected header to compare at the end
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new FileInputStream(inputFile))));

        String line;
        Set<String> expectedLines = new TreeSet();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.charAt(0) != '#') {
                break;
            }
            expectedLines.add(line);
        }

        // finally do the comparison
        assertEquals(expectedLines, actualLines);
    }

    @Test
    public void readAggregatedFile() {
        String inputFile = getClass().getResource("/evs.vcf.gz").getFile();
        VariantSource source = new VariantSource(inputFile, "evs", "evs", "Exome Variant Server");
        
        VariantReader reader = new VariantVcfReader(source, inputFile, new VariantVcfEVSFactory());

        List<Variant> variants;

        assertTrue(reader.open());
        assertTrue(reader.pre());

        int count = 0;
        while ((variants = reader.read()) != null) {
            count += variants.size();
        }

        assertEquals(count, 1000);

        assertTrue(reader.post());
        assertTrue(reader.close());
    }
}
