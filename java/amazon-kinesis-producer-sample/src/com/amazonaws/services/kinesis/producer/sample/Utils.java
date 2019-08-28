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

/*
import java.util.Properties;
import java.util.Date;

import javax.mail.Session;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.MessagingException;
import javax.mail.Message;
*/

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
        StringBuilder sb = new StringBuilder();
        sb.append(Long.toString(sequenceNumber));
        sb.append(" ");
        String crlf = System.getProperty("line.separator");
        String message =
            "HELO myhostname.hogehoge.co.jp" + crlf +
            "MAIL FROM: username@hogehoge.co.jp" + crlf +
            "RCPT TO: abcedfgh@hogehoge.co.jp" + crlf +
            "DATA" + crlf +
            "To: abcdefgh@hogehoge.co.jp" + crlf +
            "Subject: test mail no " + Long.toString(sequenceNumber) + crlf +
            "From: username@hogehoge.co.jp" + crlf + crlf +
            "this is test mail." + crlf + crlf +
            "戦略 5 邪魔を避ける：幸福な仕事環境はみな似ているものだ(プライベートオフィス、静かな環境、優れたツール、邪魔の少なさ、さらに少ない大きな会議)。不幸な仕事環境が不幸なのはそれぞれの理由による。悪い知らせは、仕事環境の変更はどんな会社でもほとんど不可能だと言うことだ。長期リースであるために、CEOでさえそれについては何もできないかもしれない。それがプライベートオフィスを持てるソフトウェア開発者がこうも少ない理由だ。これは少なくとも2つの仕方であなたの会社を損なう。第1にそれは、(他の条件が同じなら)より快適な条件を用意してくれる企業を好む、最高の開発者の採用を難しくする。第2に、邪魔の程度によっては開発者はゾーン(訳注: 没頭した生産的な状態)の中に居続けることができず、生産性は著しく低下し得る。そのような環境を逃れる方法を探すこと。たくさんのテーブルがあるが一日の大半は空いている(そして誰もあなたを見つけない)、会社のカフェテリアにラップトップを持って行く。会議室を丸一日予約し、そこでコードを書く。そしてチェックイン攻勢によって、部屋に一人でいるときにあなたがいかに多くの仕事をしたか明らかにする。この次に危機が訪れ、あなたのマネージャが明日までにこれをやるには何が必要かと聞くときに、あなたは何と言えばよいかわかるだろう。彼らはあなたのために、その日一日オフィスを確保するだろう。そしてすぐに、その生産性を一年中維持するためにはどうしたらよいか、彼らは考えるようになるだろう。仕事に遅く出てきて遅く帰る。会社の他の人たちが帰った後の時間は最も生産的であり得る。あるいは、あなたがいつも遅く出て来る開発者たちのチームにいるのであれば、午前9時に仕事を始める。他の人が出てきてあなたの邪魔をし始めるまでの2時間の間に、その日の残りよりも多くの仕事ができるだろう。emailやIMクライアントを上げっぱなしにしないこと。望むなら一時間おきにemailをチェックするのはいいが、上げっぱなしにはしないこと。" + crlf +
            "." + crlf +
            "QUIT" + crlf
            ;
        sb.append(message);
        //while (sb.length() < totalLen) {
        //    sb.append("a");
        //}
        try {
            return ByteBuffer.wrap(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a MimeMessage.
     *
     * @param host
     *            SMTP Server hostname
     * @param port
     *            SMTP Server port
     * @param from
     *            mail from
     * @param to
     *            mail to
     * @param subject
     *            mail subject
     * @param text
     *            mail body text
     * @return MimeMessage Object or null=error
     */
     /*
     private static MimeMessage generateMimeMessage(String host, String port, String from, String to, String subject, String text) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.port", port);

            Session session = Session.getDefaultInstance(prop);
            MimeMessage mime = new MimeMessage(session);
            mime.addFrom(InternetAddress.parse(from));
            Address[] tos = { new InternetAddress(to) };
            mime.setRecipients(Message.RecipientType.TO, tos);
            mime.setSubject(subject, "iso-2022-jp");
            mime.setText(text);
            mime.setSentDate(new Date());

            return mime;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    */
}
