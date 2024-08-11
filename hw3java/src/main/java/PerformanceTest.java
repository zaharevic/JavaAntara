import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.core.assertions.DslResponseAssertion;
import us.abstracta.jmeter.javadsl.core.controllers.DslSimpleController;
import us.abstracta.jmeter.javadsl.core.controllers.DslTransactionController;

public class PerformanceTest {
    // Катушка для старта
    @Test
    public void test() throws IOException{
        TestPlanStats stats = testPlan(
                vars()
                        .set("HOST", "localhost")
                        .set("PROTOCOL", "http"),
                httpDefaults()
                        .host("${HOST}")
                        .port(Integer.parseInt("23232"))
                        .protocol("${PROTOCOL}")
                        .encoding(StandardCharsets.UTF_8),
                threadGroup(1,1,
                        logout()
                ),resultsTreeVisualizer()
        ).run();
    }

    //Сценарий 1 Логин под админом
    private DslTransactionController adminCon() throws IOException {
        return transaction( "TC_AdminConnection",
                vars().set("admin_con", "true"),
                loginWithTickets()
        );
    }

    // Сценарий 2 создание пользюка
    private DslTransactionController userCreation() throws IOException{
        return transaction( "TC_UseCreation",
                adminCon(),
                httpSampler("GET /system_settings/","/system_settings/"),
                httpSampler("GET /admin/auth/user/","/admin/auth/user/"),
                httpSampler("GET /admin/auth/user/add/","/admin/auth/user/add/"),
                httpSampler("POST /admin/auth/user/add/","/admin/auth/user/add/").method(HTTPConstants.POST)
                        .param("csrfmiddlewaretoken", "${csrf}")
                        .param("username", "${__RandomString(12,qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP,username)}")
                        .rawParam("password1", "${__RandomString(32,qazwsxrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP!@#$^&*1234567890,pass)}")
                        .rawParam("password2", "${pass}")
                        .param("_save", "Save")
                        .children(
                                regexExtractor("user_id", "/user/(.*)/change/\"")
                                        .defaultValue("user_id_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                regexExtractor("username", "\"username\" value=\"(.*)\" class")
                                        .defaultValue("username_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                        .defaultValue("csrf_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("was added successfully. You may edit it again below.")
                        ),
                httpSampler("POST /admin/auth/user/__user_id__/change/","/admin/auth/user/${user_id}/change/").method(HTTPConstants.POST)
                        .param("csrfmiddlewaretoken", "${csrf}")
                        .param("username", "${username}")
                        .param("first_name", "${__RandomString(10,qazwsxedcrfvtgbyhnujmikolp,f_name)}")
                        .param("last_name", "${__RandomString(10,qazwsxedcrfvtgbyhnujmikolp,l_name)}")
                        .rawParam("email",  "${__RandomString(10,abcdefghijklmnopqrstuvwxyz,email)}@gmail.com")
                        .param("is_active", "on")
                        .param("last_login_0", "")
                        .param("last_login_1", "")
                        .param("date_joined_0", "${__time(yyyy-MM-dd,)}")
                        .rawParam("date_joined_1","${__time(hh:mm:ss,)}")
                        .param("initial-date_joined_0", "${__time(yyyy-MM-dd,)}")
                        .rawParam("initial-date_joined_1", "${__time(hh:mm:ss,)}")
                        .param("_save", "Save")
                        .children(
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("was changed successfully.")
                        ),
                httpSampler("GET /admin/auth/user/__user_id__/change/","/admin/auth/user/${user_id}/change/")
                        .children(
                                regexExtractor("f_name", "\"first_name\" value=\"(.*)\" c")
                                        .defaultValue("f_name_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                regexExtractor("l_name", "\"last_name\" value=\"(.*)\" c")
                                        .defaultValue("l_name_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                regexExtractor("email", "\"email\" value=\"(.*)\" c")
                                        .defaultValue("email_ERROR")
                                        .template("$1$")
                                        .matchNumber(1)//,
                        ),
                httpSampler("POST /admin/auth/user/__user_id__/change/","/admin/auth/user/${user_id}/change/").method(HTTPConstants.POST)
                        .param("csrfmiddlewaretoken", "${csrf}")
                        .param("username", "${username}")
                        .param("first_name", "${f_name}")
                        .param("last_name", "${l_name}")
                        .param("email","${email}")
                        .param("is_active", "on")
                        .param("is_staff", "on")
                        .param("last_login_0", "")
                        .param("last_login_1", "")
                        .param("date_joined_0", "${__time(yyyy-MM-dd,)}")
                        .rawParam("date_joined_1","${__time(hh:mm:ss,)}")
                        .param("initial-date_joined_0", "${__time(yyyy-MM-dd,)}")
                        .rawParam("initial-date_joined_1", "${__time(hh:mm:ss,)}")
                        .param("_save", "Save")
                        .children(
                                jsr223PostProcessor("FileWriter fWriter = new FileWriter(\"C:/stage/TestPlan2/users.csv\", true);\n" +
                                        "String line = vars.get(\"username\") + \";\" + vars.get(\"pass\") + \"\\n\";\n" +
                                        "fWriter.write(line);\n" +
                                        "fWriter.close();"),
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("was changed successfully.")
                        )
        );
    }

    //Сценарий 3 Создание тикета
    private DslTransactionController ticketCreation() throws IOException{
        return transaction("TC_TicketCreation",
                userCreation(),
                adminLogout(),
                loginWithTickets(),
                httpSampler("GET /tickets/submit/","/tickets/submit/"),
                httpSampler("POST /tickets/submit/","/tickets/submit/").method(HTTPConstants.POST)
                        .param("csrfmiddlewaretoken", "${csrf}")
                        .param("queue", "1")
                        .param("title", "${__RandomString(64,qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP\\,.,)}")
                        .param("body", "${__RandomString(128,qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP\\,.,)}")
                        .param("priority", "${__Random(1,5,)}")
                        .param("due_date", "${__time(yyyy-MM-dd,)} 00:00:00")
                        .param("attachment", "")
                        .param("submitter_email", "${email}")
                        .param("assigned_to", "${user_id}")
                        .children(
                                regexExtractor("ticket_number", "<h3>..-(.*)\\.(.*)<\\/h3>")
                                        .defaultValue("ticket_number_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .invertCheck()
                                        .containsSubstrings("Unless otherwise stated, all fields are required.")
                        )
        );
    }

    // Сценарий 4 Пагинация
    private DslTransactionController pagination() throws IOException{
        return transaction("TC_Pagination",
                userCreation(),
                adminLogout(),
                loginWithTickets(),
                ticketSampler("0", "10"),
                ticketSampler("${__Random(0,3,)}0", "10")
        );
    }

    // Сцеанрий 5 Фильтрация
    private DslTransactionController filtration() throws IOException{
        return transaction("TC_Filtration",
                userCreation(),
                adminLogout(),
                loginWithOutTickets(),
                ticketSampler("0","25"),
                httpSampler("GET /tickets/","/tickets/")
                        .children(
                                regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                        .defaultValue("csrf_ERROR")
                                        .template("$1$")
                                        .matchNumber(1),
                                debugPostProcessor()
                        ),
                jsr223Sampler("query_encoded >>> JSR223 PreProcessor",
                        "import java.util.Random;\n" +
                        "import java.util.Base64;\n" +
                        "Random rand = new Random();\n" +
                        "Integer filter_num;\n" +
                        "String query;\n" +
                        "switch (rand.nextInt(6)) {\n" +
                        "\tcase 0:\n" +
                        "\t\tfilter_num = rand.nextInt(1) + 1;\n" +
                        "\t\tquery = '{\"filtering\": {\"queue__id__in\": [' + filter_num + ']}, \"filtering_or\": {\"queue__id__in\": [' + filter_num + ']}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"\"}';\n" +
                        "\t\tbreak;\n" +
                        "\tcase 1:\n" +
                        "\t\tfilter_num = vars.get(\"rand_id\") as Integer;\n" +
                        "\t\tquery = '{\"filtering\": {\"assigned_to__id__in\": [' + filter_num + ']}, \"filtering_or\": {\"assigned_to__id__isnull\": true}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"\"}';\n" +
                        "\t\tbreak;\n" +
                        "\tcase 2:\n" +
                        "\t\tfilter_num = rand.nextInt(4) + 1;\n" +
                        "\t\tquery = '{\"filtering\": {\"status__in\": [' + filter_num + ']}, \"filtering_or\": {\"status__in\": [' + filter_num + ']}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"\"}';\n" +
                        "\t\tbreak;\n" +
                        "\tcase 3:\n" +
                        "\t\tfilter_num = rand.nextInt(3);\n" +
                        "\t\tif (filter_num == 0) {\n" +
                        "\t\t\tfilter_num = -1;\n" +
                        "\t\t}\n" +
                        "\t\tquery = '{\"filtering\": {\"kbitem__in\": [' + filter_num + ']}, \"filtering_or\": {\"kbitem__isnull\": true}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"\"}';\n" +
                        "\t\tbreak;\n" +
                        "\tcase 4:\n" +
                        "\t\tString date1 = \"${__RandomDate(yyyy-MM-dd,2018-01-01,2024-12-31,,)}\"; \n" +
                        "\t\tString date2 = \"${__RandomDate(yyyy-MM-dd,2020-01-01,2025-12-31,,)}\";\n" +
                        "\t\tquery = '{\"filtering\": {\"created__gte\": \"' + date1 + '\", \"created__lte\": \"' + date2 + '\"}, \"filtering_or\": {}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"\"}';\n" +
                        "\t\tbreak;\n" +
                        "\tcase 5:\n" +
                        "\t\tString randomString = \"${__RandomString(1,qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP,)}\"; \n" +
                        "\t\tquery = '{\"filtering\": {}, \"filtering_or\": {}, \"sorting\": \"created\", \"sortreverse\": null, \"search_string\": \"' + randomString + '\"}';\n" +
                        "\t\tbreak;\n" +
                        "}\n" +
                        "String encoded_query = Base64.getEncoder().encodeToString(query.getBytes());\n" +
                        "vars.put(\"query_encoded\", encoded_query);"),
                ticketSampler("0","25"),
                ifController("${__groovy(${__Random(0,9,)} == 0,)}",
                        httpSampler("POST /save_query/", "/save_query/").method(HTTPConstants.POST)
                                .param("csrfmiddlewaretoken", "${csrf}")
                                .param("query_encoded", "${query_encoded}")
                                .param("title", "${__RandomString(15,qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIK\\,OLP,)}")
                                .children(
                                        regexExtractor("query_encoded","'query_encoded' value='(.*)'/>")
                                                .template("$1$")
                                                .matchNumber(1)
                                                .defaultValue("query_encoded_ERROR")
                                ),
                        ticketSampler("0", "25")
                )

        );
    }

    //Сценраий 6 Открытие тикета
    private DslTransactionController ticketOpen() throws IOException{
        return transaction( "TC_TicketOpen",
                userCreation(),
                adminLogout(),
                loginWithOutTickets(),
                goToRandomTicket(),
                ifController("${__groovy(vars.get(\"assigned_to\") != \"Unassigned\")}",
                    httpSampler("GET /tickets/__rand_id__/", "/tickets/${rand_id}/")
                            .param("take", "")
                            .children(
                                    regexExtractor("assigned_to", "Assigned To</th>\\n *<td>(.*) <strong>")
                                            .template("$1$")
                                            .matchNumber(1)
                                            .defaultValue("assigned_to_ERROR"),
                                    responseAssertion().containsSubstrings("<td>${f_name} ${l_name}")
                            )
                )
        );
    }

    // Сценарий 7 Изменение статуса тикета
    private DslTransactionController statusEdit() throws IOException{
        return transaction("TC_StatusEdit",
                userCreation(),
                adminLogout(),
                loginWithOutTickets(),
                goToRandomTicket(),
                httpSampler("POST /tickets/__rand_id__/update/","/tickets/${rand_id}/update/").method(HTTPConstants.POST)
                        .param("comment","")
                        .param("new_status","${new_status}")
                        .param("publi$","1")
                        .param("time_spent","")
                        .param("title","${title}")
                        .param("owner","${owner_id}")
                        .param("priority","${priority}")
                        .param("due_date","")
                        .param("attachment","")
                        .param("csrfmiddlewaretoken","${csrf}")
                        .children(
                                jsr223PreProcessor("def rand = new Random()\n" +
                                        "String status = vars.get(\"status\")\n" +
                                        "System.out.println(status)\n" +
                                        "Integer status_integer\n" +
                                        "switch(status){\n" +
                                        "    case \"Open\":\n" +
                                        "        status_integer = 1\n" +
                                        "        break\n" +
                                        "    case \"Reopened\":\n" +
                                        "        status_integer = 2\n" +
                                        "        break\n" +
                                        "    case \"Resolved\":\n" +
                                        "        status_integer = 3\n" +
                                        "        break\n" +
                                        "    case \"Closed\":\n" +
                                        "        status_integer = 4\n" +
                                        "        break\n" +
                                        "    case \"Duplicate\":\n" +
                                        "        status_integer = 5\n" +
                                        "        break\n" +
                                        "    default:\n" +
                                        "        AssertionResult.setFailure(true)\n" +
                                        "}\n" +
                                        "System.out.println(status_integer);\n" +
                                        "Integer new_status = status_integer;\n" +
                                        "System.out.println(new_status);\n" +
                                        "while(new_status == status_integer){ //небольшой костыль)))\n" +
                                        "\tnew_status = rand.nextInt(4) + 1;\n" +
                                        "\tSystem.out.println(new_status);\n" +
                                        "}\n" +
                                        "vars.put(\"new_status\", String.valueOf(new_status));")
                        )
        );
    }

    // Скрипт 8 удаление тикета
    private DslTransactionController ticketDelete() throws IOException{
        return transaction("TC_TicketDelete",
                userCreation(),
                adminLogout(),
                loginWithTickets(),
                httpSampler("GET /tickets/","/tickets/")
                        .param("status", "5")
                        .param("date_from", "")
                        .param("date_to", "")
                        .param("q", "")
                        .children(
                                regexExtractor("query_encoded", "'query_encoded' value='(.*)'/>")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("query_encoded_ERROR")
                        ),
                ticketSampler("0","25"),
                ifController( "${__groovy(vars.get(\"records\") != \"0\")}",
                        httpSampler("GET /tickets/__rand_id__/", "/tickets/${rand_id}/"),
                        httpSampler("GET /tickets/__rand_id__/delete/" , "/tickets/${rand_id}/delete/"),
                        httpSampler("POST /tickets/__rand_id__/delete/","/tickets/${rand_id}/delete/").method(HTTPConstants.POST)
                                .param("csrfmiddlewaretoken", "${csrf}")
                                .param("next", "home"),
                        ticketSampler("0","25")
                )
                );
    }

    // Сценарий 9 Закрытие тикета
    private DslTransactionController closeTicket() throws IOException{
        return transaction("TC_CloseTicket",
                userCreation(),
                adminLogout(),
                loginWithOutTickets(),
                ticketSampler("0","25"),
                httpSampler("GET /tickets/__rand_id__/", "/tickets/${rand_id}/")
                        .children(
                                regexExtractor("owner_names","info'>by (.*), <")
                                        .template("$1$")
                                        .matchNumber(-1)
                                        .defaultValue("owner_names_ERROR"),
                                regexExtractor("owner_id","<option value='(.{1,9})' >${owner_name}")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("1"),
                                regexExtractor("csrf","\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("csrf_ERROR"),
                                regexExtractor("title","colspan='4'><h3>.*\\. (.*) \\[")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("title_ERROR"),
                                regexExtractor("priority","<td class=\"(table-warning)?\">(.)\\.")
                                        .template("$2$")
                                        .matchNumber(1)
                                        .defaultValue("priority_ERR"),
                                regexExtractor("public","name='public' value='(.)'")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("public_ERROR"),
                                jsr223PostProcessor("" +
                                        "int i = 1\n" +
                                        "flag = true;\n" +
                                        "String answer;\n" +
                                        "while(flag){\n" +
                                        "\tString name = vars.get(\"owner_names_\" + i);\n" +
                                        "\tif(name != null){\n" +
                                        "\t\tanswer = name;\n" +
                                        "\t\ti++;\n" +
                                        "\t}\n" +
                                        "\telse{\n" +
                                        "\t\tflag=false;\n" +
                                        "\t}\n" +
                                        "}\n" +
                                        "System.out.println(answer)\n" +
                                        "vars.put(\"owner_name\", answer);\n")
                        ),
                httpSampler("POST /tickets/__rand_id__/update/","/tickets/${rand_id}/update/").method(HTTPConstants.POST)
                        .param("comment","")
                        .param("new_status","4")
                        .param("public","${public}")
                        .param("time_spent","")
                        .param("title","${title}")
                        .param("owner","${owner_id}")
                        .param("priority","${priority}")
                        .param("due_date","${__time(yyyy-MM-dd,)} 00:00:00")
                        .param("attachment","")
                        .param("csrfmiddlewaretoken","${csrf}")
                        .children(
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("[Closed]</h3>")
                        )
        );
    }

    // Сценарий 10 Выход
    public DslTransactionController logout() throws IOException{
        return transaction("TC_Logout",
                userCreation(),
                adminLogout(),
                loginWithTickets(),
                httpSampler("GET /logout/","/logout/")
                        .children(
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("</i> Log In</a>")
                        )
                );
    }

    public DslSimpleController adminLogout() throws IOException{
        return simpleController(
                httpSampler("GET /admin/logout/","/admin/logout/")
                        .children(
                                responseAssertion()
                                        .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                        .containsSubstrings("Logged out")
                        )
        );
    }

    private DslSimpleController loginWithOutTickets() throws IOException {
        return simpleController(
                httpSampler("GET /","/")
                        .children(
                                jsr223PreProcessor("def filePath = 'C:/stage/TestPlan2/users.csv' //путь к файлу\n" +
                                        "def lines = new File(filePath).readLines() // файл открывается для чтения\n" +
                                        "def lastLine = lines[-1] // считывается последняя строка\n" +
                                        "def (username, pass) = lastLine.split(';') // строка парсится по символу \";\"\n" +
                                        "vars.put(\"username\", username) // сохрнение username в vars\n" +
                                        "vars.put(\"pass\", pass) // сохрнение pass в vars")
                        ),
                httpSampler("GET /login/","/login/")
                        .param("next", "/")
                        .children(
                                regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                        .defaultValue("csrf_ERROR")
                                        .template("$1$")
                                        .matchNumber(1)
                        ),
                httpSampler("POST /login/", "/login/").method(HTTPConstants.POST)
                        .param("username", "${username}")
                        .param("password", "${pass}")
                        .param("next", "/")
                        .param("csrfmiddlewaretoken", "${csrf}")
                        .children(
                                regexExtractor("sess_id", "sessionid=(.*)")
                                        .defaultValue("sess_id_ERROR")
                                        .template("$1$")
                                        .matchNumber(1)
                        )
        );
    }

    public DslSimpleController loginWithTickets() throws IOException {
        return simpleController(
                ifController("${__groovy(!vars.get('admin_con').equals('true'))}",
                        httpSampler("GET /","/").children(
                                jsr223PreProcessor("def filePath = 'C:/stage/TestPlan2/users.csv' //путь к файлу\n" +
                                        "def lines = new File(filePath).readLines() // файл открывается для чтения\n" +
                                        "def lastLine = lines[-1] // считывается последняя строка\n" +
                                        "def (username, pass) = lastLine.split(';') // строка парсится по символу \";\"\n" +
                                        "vars.put(\"username\", username) // сохрнение username в vars\n" +
                                        "vars.put(\"pass\", pass) // сохрнение pass в vars")
                        ),
                        httpSampler("GET /login/","/login/")
                                .rawParam("next","/")
                                .children(
                                        regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                                .defaultValue("csrf_ERROR")
                                                .template("$1$")
                                                .matchNumber(1)
                                ),
                        httpSampler("POST /login/","/login/").method(HTTPConstants.POST)
                                .param("username", "${username}")
                                .param("next", "/")
                                .param("password", "${pass}")
                                .param("csrfmiddlewaretoken", "${csrf}")
                                .children(
                                        regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                                .defaultValue("csrf_ERROR")
                                                .template("$1$")
                                                .matchNumber(1),
                                        regexExtractor("query_encoded", "'query_encoded' value='(.*)'/>")
                                                .defaultValue("query_encoded_ERROR")
                                                .template("$1$")
                                                .matchNumber(1),
                                        responseAssertion()
                                                .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                                .containsSubstrings("admin")
                                ),
                        ticketSampler("0", "25")
                ),
                ifController("${__groovy(vars.get('admin_con').equals('true'))}",
                        httpSampler("GET /","/")
                                .children(
                                        jsr223PostProcessor("vars.put('admin_con', 'false');")
                                ),
                        httpSampler("GET /login/","/login/")
                                .rawParam("next", "/")
                                .children(
                                        regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                                .defaultValue("csrf_ERROR")
                                                .template("$1$")
                                                .matchNumber(1)
                                ),
                        httpSampler("POST /login/","/login/")
                                .method(HTTPConstants.POST)
                                .param("username", "admin")
                                .param("password", "admindev")
                                .param("csrfmiddlewaretoken", "${csrf}")
                                .children(
                                        regexExtractor("csrf", "\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                                .defaultValue("csrf_ERROR")
                                                .template("$1$")
                                                .matchNumber(1),
                                        regexExtractor("query_encoded", "'query_encoded' value='(.*)'/>")
                                                .defaultValue("query_encoded_ERROR")
                                                .template("$1$")
                                                .matchNumber(1),
                                        responseAssertion()
                                                .fieldToTest(DslResponseAssertion.TargetField.RESPONSE_BODY)
                                                .containsSubstrings("admin")
                                ),
                        ticketSampler("0", "25")
                )
        );
    }

    private DslSimpleController goToRandomTicket() throws IOException{
        return simpleController(
                ticketSampler("0","25"),
                httpSampler("GET /tickets/__rand_id__/", "/tickets/${rand_id}/")
                        .children(
                                regexExtractor("owner_names","info'>by (.*), <")
                                        .template("$1$")
                                        .matchNumber(-1)
                                        .defaultValue("owner_names_ERROR"),
                                regexExtractor("owner_id","<option value='(.{1,9})' >${owner_name}")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("1"),
                                regexExtractor("csrf","\"csrfmiddlewaretoken\" value=\"(.*)\"")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("csrf_ERROR"),
                                regexExtractor("title","colspan='4'><h3>.*\\. (.*) \\[")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("title_ERROR"),
                                regexExtractor("priority","<td class=\"(table-warning)?\">(.)\\.")
                                        .template("$2$")
                                        .matchNumber(1)
                                        .defaultValue("priority_ERR"),
                                regexExtractor("status"," \\[(.*)\\]<\\/h3>")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("status_ERROR"),
                                regexExtractor("assigned_to","Assigned To</th>\\n *<td>(.*) <strong>")
                                        .template("$1$")
                                        .matchNumber(1)
                                        .defaultValue("assigned_to_ERROR"),
                                jsr223PostProcessor("" +
                                        "int i = 1\n" +
                                        "flag = true;\n" +
                                        "String answer;\n" +
                                        "while(flag){\n" +
                                        "\tString name = vars.get(\"owner_names_\" + i);\n" +
                                        "\tif(name != null){\n" +
                                        "\t\tanswer = name;\n" +
                                        "\t\ti++;\n" +
                                        "\t}\n" +
                                        "\telse{\n" +
                                        "\t\tflag=false;\n" +
                                        "\t}\n" +
                                        "}\n" +
                                        "System.out.println(answer)\n" +
                                        "vars.put(\"owner_name\", answer);\n")
                        )
        );
    }

    public DslSimpleController ticketSampler(String start, String len) throws IOException {
        return simpleController(
                httpSampler("GET /datatables_ticket_list/__query_encoded__","/datatables_ticket_list/${query_encoded}")
                        .param("draw", "1")
                        .param("columns[0][data]", "id")
                        .param("columns[0][name]", "")
                        .param("columns[0][searchable]", "true")
                        .param("columns[0][orderable]", "false")
                        .param("columns[0][search][value]", "")
                        .param("columns[0][search][regex]", "false")
                        ///
                        .param("columns[1][data]", "ticket")
                        .param("columns[1][name]", "")
                        .param("columns[1][searchable]", "true")
                        .param("columns[1][orderable]", "true")
                        .param("columns[1][search][value]", "")
                        .param("columns[1][search][regex]", "false")
                        ///
                        .param("columns[2][data]", "priority")
                        .param("columns[2][name]", "")
                        .param("columns[2][searchable]", "true")
                        .param("columns[2][orderable]", "true")
                        .param("columns[2][search][value]", "")
                        .param("columns[2][search][regex]", "false")
                        ///
                        .param("columns[3][data]", "queue")
                        .param("columns[3][name]", "")
                        .param("columns[3][searchable]", "true")
                        .param("columns[3][orderable]", "true")
                        .param("columns[3][search][value]", "")
                        .param("columns[3][search][regex]", "false")
                        ///
                        .param("columns[4][data]", "status")
                        .param("columns[4][name]", "")
                        .param("columns[4][searchable]", "true")
                        .param("columns[4][orderable]", "true")
                        .param("columns[4][search][value]", "")
                        .param("columns[4][search][regex]", "false")
                        ///
                        .param("columns[5][data]", "created")
                        .param("columns[5][name]", "")
                        .param("columns[5][searchable]", "true")
                        .param("columns[5][orderable]", "true")
                        .param("columns[5][search][value]", "")
                        .param("columns[5][search][regex]", "false")
                        ///
                        .param("columns[6][data]", "due_date")
                        .param("columns[6][name]", "")
                        .param("columns[6][searchable]", "true")
                        .param("columns[6][orderable]", "true")
                        .param("columns[6][search][value]", "")
                        .param("columns[6][search][regex]", "false")
                        //
                        .param("columns[7][data]", "assigned_to")
                        .param("columns[7][name]", "")
                        .param("columns[7][searchable]", "true")
                        .param("columns[7][orderable]", "true")
                        .param("columns[7][search][value]", "")
                        .param("columns[7][search][regex]", "false")
                        ///
                        .param("columns[8][data]", "submitter")
                        .param("columns[8][name]", "")
                        .param("columns[8][searchable]", "true")
                        .param("columns[8][orderable]", "false")
                        .param("columns[8][search][value]", "")
                        .param("columns[8][search][regex]", "false")
                        ///
                        .param("columns[9][data]", "time_spent")
                        .param("columns[9][name]", "")
                        .param("columns[9][searchable]", "true")
                        .param("columns[9][orderable]", "true")
                        .param("columns[9][search][value]", "")
                        .param("columns[9][search][regex]", "false")
                        ///
                        .param("columns[10][data]", "kbitem")
                        .param("columns[10][name]", "")
                        .param("columns[10][searchable]", "true")
                        .param("columns[10][orderable]", "true")
                        .param("columns[10][search][value]", "")
                        .param("columns[10][search][regex]", "false")
                        ///
                        .param("order[0][column]", "0")
                        .param("order[0][dir]", "asc")
                        .param("start", start)
                        .param("lenght", len)
                        .param("search[value]", "")
                        .param("search[regex]", "false")
                        .children(
                                jsonExtractor("rand_id","data[*].id")
                                        .matchNumber(0)
                                        .defaultValue("rand_id_ERROR")
                        )
        );
    }

}