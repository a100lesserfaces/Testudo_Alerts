

public class ReadTest {
  public static void main(String[] args) {
    RSSFeedParser parser = new RSSFeedParser("https://news.google.com/news/feeds?pz=1&cf=all&ned=en&hl=en&q=college+park+md&output=rss");
    Feed feed = parser.readFeed();
    for (FeedMessage message : feed.getMessages()) {
      System.out.println(message);

    }

  }
}
