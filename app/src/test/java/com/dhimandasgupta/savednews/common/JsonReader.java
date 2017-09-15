package com.dhimandasgupta.savednews.common;

import com.dhimandasgupta.savednews.rest.ArticleResponse;
import com.google.gson.Gson;

/**
 * Created by dhimandasgupta on 11/09/17.
 */

public class JsonReader {
    public static final String SUCCESS_CONTENT = "{\n" +
            "  \"status\": \"ok\",\n" +
            "  \"source\": \"abc-news-au\",\n" +
            "  \"sortBy\": \"top\",\n" +
            "  \"articles\": [\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/4172326\",\n" +
            "      \"title\": \"Teen stabbed grandfather, applied bandaid, then 'cleaned dishes as he died'\",\n" +
            "      \"description\": \"Court documents reveal a Queensland teenager's hesitation before she stabbed her 81-year-old granddad.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/robert-whitwell-murder-court-hears-how-teen-stabbed-grandfather/8893632\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8760378-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T14:47:37Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": null,\n" +
            "      \"title\": \"Irma weakens to a tropical storm; rescues underway across Florida\",\n" +
            "      \"description\": \"Downgraded from a hurricane to a tropical storm, Irma continues to flood Florida with heavy rain and high storm surge.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-12/irma-weakens-as-rescue-missions-underway-across-florida/8894066\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8894162-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T16:06:12Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/jonathan-hepburn/7113236\",\n" +
            "      \"title\": \"Same-sex marriage? Q&A asks whether we should keep marriage at all\",\n" +
            "      \"description\": \"With same-sex marriage about to be put to a postal survey of the electorate, Q&A raised the much more basic question of whether marriage has any place in society at all, or is a sexist, dangerous institution we should get rid of.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-12/q-and-a-should-anyone-get-married-at-all/8893630\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8894140-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T14:30:37Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/louise--yaxley/5553226, http://www.abc.net.au/news/matt-wordsworth/4148794\",\n" +
            "      \"title\": \"AGL appears committed to closing Liddell plant despite meeting with PM\",\n" +
            "      \"description\": \"Energy provider AGL appears to be committed to closing the Liddell power station, despite the Government declaring it could remain open.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/agl-appears-committed-to-closing-liddell-power-station/8893822\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8877002-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T14:05:33Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/linton-besser/7278682\",\n" +
            "      \"title\": \"Many tipped the report on alleged water corruption to be a whitewash. Instead, it's a grenade\",\n" +
            "      \"description\": \"The Government has released a wide-ranging report into the Murray-Darling Basin Plan, and it is nothing of the whitewash many expected.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/murray-darling-basin-plan-grenade-report-icac-four-corners/8893456\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/7219978-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T14:06:43Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/ruby-jones/5939968\",\n" +
            "      \"title\": \"'It's not good for the kids': Push to end orphanage volunteering as travel company stops student trips\",\n" +
            "      \"description\": \"The trend of Australian high school students going overseas to volunteer in an orphanage is about to come to an abrupt end.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-12/world-challenge-to-end-student-volunteer-trips-to-orphanages/8892142\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8892626-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T14:03:43Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": null,\n" +
            "      \"title\": \"'Everybody loved him': Man who drowned trying to save child remembered as a hero\",\n" +
            "      \"description\": \"The family of father Shaun Oliver, who drowned while trying to rescue a child caught in a rip at a beach south of Sydney, pay tribute to the father of three as a larrikin who would help anyone.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/shaun-oliver-remembered-as-larrikin-who-would-help-anyone/8893330\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8892392-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T10:52:32Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/michael-janda/166854\",\n" +
            "      \"title\": \"Banks warned over default risk from $500b in 'liar loan' mortgages\",\n" +
            "      \"description\": \"Up to a third of Australian mortgages could be liar loans based on inaccurate information, warns investment bank UBS, raising the risk of widespread home loan defaults.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/500b-dollars-of-liar-loans-in-australia-ubs/8892030\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/5438986-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T07:10:16Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": null,\n" +
            "      \"title\": \"North Korea warns US of 'greatest pain in history' if new sanctions are imposed\",\n" +
            "      \"description\": \"North Korea warns it will make the US pay a heavy price if tougher sanctions against Pyongyang are approved by the UN Security Council.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/north-korea-warns-of-harsh-response-if-new-sanctions-are-imposed/8892326\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8855948-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T04:50:53Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"author\": \"http://www.abc.net.au/news/mary-lloyd/4754676\",\n" +
            "      \"title\": \"The Australian artist who captured the horror of 9/11 on film\",\n" +
            "      \"description\": \"Chris Hopewell was in New York when he caught what is now world-famous vision of the second plane flying into the World Trade Centre.\",\n" +
            "      \"url\": \"http://www.abc.net.au/news/2017-09-11/the-australian-artist-who-documented-9-11/8893758\",\n" +
            "      \"urlToImage\": \"http://www.abc.net.au/news/image/8893912-1x1-700x700.jpg\",\n" +
            "      \"publishedAt\": \"2017-09-11T09:20:59Z\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static ArticleResponse getArticleResponseSuccess() {
        final Gson gson = new Gson();
        return gson.fromJson(SUCCESS_CONTENT, ArticleResponse.class);
    }
}
