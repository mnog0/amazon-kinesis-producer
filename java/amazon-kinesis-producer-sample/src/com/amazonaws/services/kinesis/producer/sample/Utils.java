/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.kinesis.producer.sample;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Properties;
import java.util.Date;

import javax.mail.Session;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.PasswordAuthentication;
//import javax.mail.internet.AddressException;
//import javax.mail.MessagingException;
import javax.mail.Message;

public class Utils {
    private static final Random RANDOM = new Random();

    /**
     * @return A random unsigned 128-bit int converted to a decimal string.
     */
    public static String randomExplicitHashKey() {
        return new BigInteger(128, RANDOM).toString(10);
    }

    /**
     * Generates a blob containing a UTF-8 string. The string begins with the
     * sequence number in decimal notation, followed by a space, followed by
     * padding.
     *
     * @param sequenceNumber
     *            The sequence number to place at the beginning of the record
     *            data.
     * @param totalLen
     *            Total length of the data. After the sequence number, padding
     *            is added until this length is reached.
     * @return ByteBuffer containing the blob
     */
    public static ByteBuffer generateData(long sequenceNumber, int totalLen) {

        final String to = "to_xon_test_account@xon.local";
        final String from = "from_xon_test_account@xon.local";
        //final String charset = "ISO-2022-JP";
        final String charset = "UTF-8";
        final String encoding = "base64";

        String subject = "JavaMail テストメール";
        String context = "テストメールの本文";

        StringBuilder sb = new StringBuilder();
        sb.append(Long.toString(sequenceNumber));
        sb.append(" ");
        sb.append(send(to, from, subject, context));
        /*while (sb.length() < totalLen) {
            sb.append("a");
        }*/
        try {
            return ByteBuffer.wrap(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String send(String from, String to, String subject, String text) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "localhost");
            prop.put("mail.smtp.port", "localhost");
            Session session = Session.getDefaultInstance(prop);
            MimeMessage mime = new MimeMessage(session);
            mime.addFrom(InternetAddress.parse(from));
            Address[] tos = { new InternetAddress(to) };
            mime.setRecipients(Message.RecipientType.TO, tos);
            mime.setSubject(subject, "iso-2022-jp");
            mime.setText(text);
            mime.setSentDate(new Date());
            return mime.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
