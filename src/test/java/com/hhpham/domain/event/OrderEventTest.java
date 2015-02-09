package com.hhpham.domain.event;

import com.thoughtworks.xstream.XStream;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderEventTest {

    XStream xStream = new XStream();

    final String orderEventString2 = "<event><creator><address><fullName></fullName></address><email>pham.hh\n" +
            "@gmail.com</email><firstName>Hoang Ha</firstName><language>en</language><lastName>Pham</lastName><openId>https://www.appdirect.com/openid/id/df7c13e4-\n" +
            "0256-404d-9f0b-36f85ca27e9f</openId><uuid>df7c13e4-0256-404d-9f0b-36f85ca27e9f</uuid></creator><flag>DEVELOPMENT</flag><marketplace><baseUrl>https://w\n" +
            "ww.appdirect.com</baseUrl><partner>APPDIRECT</partner></marketplace><payload><company><country>CA</country><name>integration challenge</name><uuid>fda5c028-614a-4983-a1ef-61e7ecc9c8c6</uuid><website>integrationChallenge.com</website></company><configuration/><order><editionCode>FREE</editionCode><pricingDuration>MONTHLY</pricingDuration></order></payload><type>SUBSCRIPTION_ORDER</type></event>";

    final String orderEventString = "<event>\n" +
            "    <type>SUBSCRIPTION_ORDER</type>\n" +
            "    <marketplace>\n" +
            "        <baseUrl>https://www.acme-marketplace.com</baseUrl>\n" +
            "        <partner>ACME</partner>\n" +
            "    </marketplace>\n" +
            "    <creator>\n" +
            "        <email>admin@fakeco</email>\n" +
            "        <firstName>Alice</firstName>\n" +
            "        <lastName>Hacker</lastName>\n" +
            "        <openId>https://www.acme-marketplace.com/openid/id/a11a7918-bb43-4429-a256-f6d729c71033</openId>\n" +
            "        <uuid>a11a7918-bb43-4429-a256-f6d729c71033</uuid>\n" +
            "    </creator>\n" +
            "    <payload>\n" +
            "        <company>\n" +
            "            <uuid>d15bb36e-5fb5-11e0-8c3c-00262d2cda03</uuid>\n" +
            "            <email>admin@fakeco</email>\n" +
            "            <name>Fake Co.</name>\n" +
            "            <phoneNumber>1-415-555-1212</phoneNumber>\n" +
            "            <website>fakeco</website>\n" +
            "        </company>\n" +
            "        <order>\n" +
            "            <editionCode>BASIC</editionCode>\n" +
            "            <item>\n" +
            "                <quantity>10</quantity>\n" +
            "                <unit>USER</unit>\n" +
            "            </item>\n" +
            "        </order>\n" +
            "    </payload>\n" +
            "</event>";

    @Before
    public void setup() {
        xStream.alias("event", OrderEvent.class);
    }

    @Test
    public void testAssemble() {

        final Object o = xStream.fromXML(orderEventString);
        final Object o1 = xStream.fromXML(orderEventString2);

        assertThat(((OrderEvent) o).getPayload().getCompany().getUuid()).isEqualTo("d15bb36e-5fb5-11e0-8c3c-00262d2cda03");
        assertThat(((OrderEvent) o1).getPayload().getCompany().getUuid()).isEqualTo("fda5c028-614a-4983-a1ef-61e7ecc9c8c6");

    }

    @Test
    public void testPrintOrderToFile() throws IOException {
        final String fileName = "ordersTest.txt";
        PrintWriter out = new PrintWriter(fileName);

        OrderEvent orderEvent = (OrderEvent)xStream.fromXML(orderEventString);
        out.print(orderEvent.getPayload());

        out.close();

        // append another order
        PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        OrderEvent orderEvent2 = (OrderEvent)xStream.fromXML(orderEventString2);
        out2.println("\n" + orderEvent2.getPayload());

        out2.close();

        List<String> lines = Files.readAllLines(Paths.get(fileName),
                Charset.defaultCharset());
        for (String line : lines) {
            System.out.println(line + "\n");
        }
    }



}