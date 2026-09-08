package com.biel.lobby.guide;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/** Run with ./gradlew verifyGameGuide; no running Minecraft server is needed. */
public final class GameGuideTest {
    public static void main(String[] args) throws Exception {
        List<String> twelveRows = java.util.Collections.nCopies(12, "Una línia.");
        require(BookLayout.paginate("Títol", twelveRows).size() == 1, "Header must leave twelve body rows");
        List<String> thirteenRows = java.util.Collections.nCopies(13, "Una línia.");
        require(BookLayout.paginate("Títol", thirteenRows).size() == 2, "Header must count on every page");

        String longParagraph = "Protegeix el company que porta el pic de diamant. ".repeat(30).strip();
        List<BookLayout.Page> longPages = BookLayout.paginate("Un títol que ocupa més d'una línia", List.of(longParagraph));
        verifyBounds(longPages);
        String recovered = longPages.stream().flatMap(page -> page.body().stream()).reduce((a, b) -> a + " " + b).orElse("");
        require(recovered.equals(longParagraph), "Long paragraphs must retain every word in order");
        String longWord = "W".repeat(150);
        List<BookLayout.Page> wordPages = BookLayout.paginate("Paraula", List.of(longWord));
        verifyBounds(wordPages);
        require(wordPages.stream().flatMap(page -> page.body().stream()).reduce("", String::concat).equals(longWord),
                "Long words must split without losing characters");
        verifyBounds(BookLayout.paginate("Guàrdia", List.of("", "Íïí àèéòóú ç l·l ".repeat(12), "", "", "Fi", "")));

        GameGuide included = new GameGuide("Included");
        included.parse(List.of("## objecte TEST", "- Primera línia", "- i segona línia.",
                "## pàgina Prova", "{{objecte TEST}}", "", "Preu: {OR} or."));
        included.withValues(Map.of("OR", "12"));
        String includedText = plain(included.book().pages().getFirst());
        require(includedText.contains("Primera línia i"), "Tooltip line breaks should reflow as book prose");
        require(includedText.contains("12 or."), "Values must be substituted");
        require(!includedText.contains("{{"), "Includes must be expanded");

        Path guidePath = Path.of("src/main/resources/guides/obsidian-defenders.md");
        List<String> guideLines = Files.readAllLines(guidePath);
        GameGuide guide = new GameGuide("Obsidian Defenders");
        guide.parse(guideLines);
        // Two-digit values exercise the narrow page layout even for single-digit timings.
        Map<String, String> values = new HashMap<>();
        var placeholders = Pattern.compile("(?<!\\{)\\{([\\p{L}_]+)}(?!})").matcher(String.join("\n", guideLines));
        while (placeholders.find()) values.put(placeholders.group(1), "12");
        guide.withValues(values);
        List<Component> pages = guide.book().pages();
        List<String> rendered = new ArrayList<>();
        for (Component page : pages) {
            String text = plain(page);
            require(!text.contains("{"), "No unresolved placeholders in the book");
            String[] headingAndBody = text.split("\n\n", 2);
            require(headingAndBody.length == 2, "Every page needs a heading and body");
            verifyBounds(List.of(new BookLayout.Page(List.of(headingAndBody[0].split("\n")), List.of(headingAndBody[1].split("\n")))));
            require(page.color() == NamedTextColor.BLACK, "Body ink must be explicitly black");
            require(page.children().getFirst().decoration(TextDecoration.BOLD) == TextDecoration.State.TRUE,
                    "Headings must retain bold styling");
            rendered.add(text);
        }
        long authoredPages = guideLines.stream().filter(line -> line.startsWith("## pàgina ")).count();
        Path reportDirectory = Path.of("build/reports/game-guide");
        Files.createDirectories(reportDirectory);
        Files.writeString(reportDirectory.resolve("pages.json"), new Gson().toJson(rendered));
        Files.writeString(reportDirectory.resolve("pages.txt"), String.join("\n\n---\n\n", rendered));
        require(pages.size() == authoredPages, "Each authored topic should fit one page: " + pages.size() + " pages for " + authoredPages + " topics");
        System.out.println("GameGuideTest: layout, long text, includes and " + pages.size() + " guide pages passed");
    }

    private static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    private static void verifyBounds(List<BookLayout.Page> pages) {
        for (BookLayout.Page page : pages) {
            require(page.heading().size() + 1 + page.body().size() <= 14, "Page exceeds fourteen rows");
            require(!page.body().getFirst().isBlank() && !page.body().getLast().isBlank(), "No blank page edges");
            for (String row : page.heading()) require(BookLayout.width(row, true) <= 114, "Heading is too wide");
            for (String row : page.body()) require(BookLayout.width(row, false) <= 114, "Body row is too wide");
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
