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

        int i = 0;
        do {
            variants = reader.read();
            if(variants != null){
                i+=variants.size();
            }
        } while (variants != null);

        assertEquals(i, 999);

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

        int i = 0;
        do {
            variants = reader.read();
            if(variants != null){
                i+=variants.size();
            }
        } while (variants != null);

        assertEquals(753, i);

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

        assertNotNull(source.getMetadata().get("contig"));
        assertFalse(((Collection)source.getMetadata().get("contig")).isEmpty());
        assertTrue(((Collection)source.getMetadata().get("contig")).iterator().next() instanceof String);
        assertNotNull(source.getMetadata().get("FILTER"));
        assertFalse(((Collection)source.getMetadata().get("FILTER")).isEmpty());
        assertTrue(((Collection)source.getMetadata().get("FILTER")).iterator().next() instanceof VcfFilterHeader);
        assertNotNull(source.getMetadata().get("ALT"));
        assertFalse(((Collection)source.getMetadata().get("ALT")).isEmpty());
        assertTrue(((Collection)source.getMetadata().get("ALT")).iterator().next() instanceof VcfAlternateHeader);
        assertNotNull(source.getMetadata().get("FORMAT"));
        assertFalse(((Collection)source.getMetadata().get("FORMAT")).isEmpty());
        assertTrue(((Collection)source.getMetadata().get("FORMAT")).iterator().next() instanceof VcfFormatHeader);
        assertNotNull(source.getMetadata().get("INFO"));
        assertFalse(((Collection)source.getMetadata().get("INFO")).isEmpty());
        assertTrue(((Collection)source.getMetadata().get("INFO")).iterator().next() instanceof VcfInfoHeader);
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

        int i = 0;
        do {
            variants = reader.read();
            if(variants != null){
                i+=variants.size();
            }
        } while (variants != null);

        assertEquals(i, 1000);

        assertTrue(reader.post());
        assertTrue(reader.close());
    }
}
