package com.biel.lobby.guide;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/** Explicit rows keep the client from clipping paragraphs at the bottom of a book. */
final class BookLayout {
    static final int PAGE_WIDTH = 114;
    static final int PAGE_ROWS = 14;

    record Page(List<String> heading, List<String> body) {}

    static List<Page> paginate(String title, List<String> paragraphs) {
        List<String> heading = wrap(title, true);
        if (heading.size() > 3) throw new IllegalArgumentException("Guide heading is too long: " + title);
        int bodyRows = PAGE_ROWS - heading.size() - 1;
        List<Page> pages = new ArrayList<>();
        List<String> body = new ArrayList<>();
        for (String paragraph : paragraphs) {
            if (paragraph.isBlank()) {
                if (!body.isEmpty() && !body.getLast().isEmpty()) body.add("");
                continue;
            }
            List<String> rows = wrap(paragraph, false);
            // Keep a paragraph together when it fits on a fresh page.
            if (!body.isEmpty() && rows.size() <= bodyRows && body.size() + rows.size() > bodyRows) {
                addPage(pages, heading, body);
                body = new ArrayList<>();
            }
            for (String row : rows) {
                if (body.size() >= bodyRows) {
                    addPage(pages, heading, body);
                    body = new ArrayList<>();
                }
                body.add(row);
            }
        }
        if (!body.isEmpty()) addPage(pages, heading, body);
        return pages;
    }

    private static void addPage(List<Page> pages, List<String> heading, List<String> body) {
        while (!body.isEmpty() && body.getLast().isBlank()) body.removeLast();
        if (!body.isEmpty()) pages.add(new Page(List.copyOf(heading), List.copyOf(body)));
    }

    static List<String> wrap(String text, boolean bold) {
        List<String> rows = new ArrayList<>();
        StringBuilder row = new StringBuilder();
        for (String word : text.strip().split("\\s+")) {
            if (word.isEmpty()) continue;
            if (!row.isEmpty() && width(row + " " + word, bold) > PAGE_WIDTH) {
                rows.add(row.toString());
                row.setLength(0);
            }
            if (!row.isEmpty()) row.append(' ');
            for (int codePoint : word.codePoints().toArray()) {
                String glyph = Character.toString(codePoint);
                if (!row.isEmpty() && width(row + glyph, bold) > PAGE_WIDTH) {
                    rows.add(row.toString());
                    row.setLength(0);
                }
                row.append(glyph);
            }
        }
        if (!row.isEmpty()) rows.add(row.toString());
        return rows;
    }

    static int width(String text, boolean bold) {
        return text.codePoints().map(codePoint -> advance(codePoint, bold)).sum();
    }

    private static int advance(int codePoint, boolean bold) {
        // Default Latin bitmap font advances, including the gap after each glyph.
        // Accented letters use their base letter; unknown glyphs get a wide allowance.
        String normalized = Normalizer.normalize(Character.toString(codePoint), Normalizer.Form.NFD);
        int base = normalized.codePointAt(0);
        int advance = switch (base) {
            case ' ' -> 4;
            case 'i', '!', '.', ',', ':', ';', '|', '\'' -> 2;
            case 'l', '`' -> 3;
            case 'I', 't', '[', ']', '"' -> 4;
            case 'f', 'k', '(', ')', '{', '}', '<', '>' -> 5;
            case '@', '~' -> 7;
            default -> base >= 32 && base <= 126 || base == 183 ? 6 : 9;
        };
        if (codePoint != base) advance = Math.max(6, advance);
        return advance + (bold ? 1 : 0);
    }
}
