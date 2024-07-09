package com.gildedrose;

class GildedRose {
    Item[] items;

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int LEGENDARY_QUALITY = 80;
    private static final int QUALITY_CHANGE_STANDARD = 1;
    private static final int QUALITY_CHANGE_CONJURED = 2;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private void increaseQuality(Item item, int times) {
        for (int i = 0; i < times; i++) {
            if (item.quality < MAX_QUALITY) {
                item.quality++;
            }
        }
    }

    private void decreaseQuality(Item item, int times) {
        for (int i = 0; i < times; i++) {
            if (item.quality > MIN_QUALITY) {
                item.quality--;
            }
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    private void conjuredHandler(Item item) {
        if (item.sellIn < 1) {
            decreaseQuality(item, QUALITY_CHANGE_CONJURED * 2);
        } else {
            decreaseQuality(item, QUALITY_CHANGE_CONJURED);
        }
        decreaseSellIn(item);
    }

    private void backstageHandler(Item item) {
        if (item.sellIn > 10) {
            increaseQuality(item, QUALITY_CHANGE_STANDARD);
        } else if (item.sellIn > 5) {
            increaseQuality(item, QUALITY_CHANGE_STANDARD * 2);
        } else if (item.sellIn > 0) {
            increaseQuality(item, QUALITY_CHANGE_STANDARD * 3);
        } else {
            item.quality = MIN_QUALITY;
        }
        decreaseSellIn(item);
    }

    private void agedBrieHandler(Item item) {
        if (item.sellIn < 1) {
            increaseQuality(item, QUALITY_CHANGE_STANDARD * 2);
        } else {
            increaseQuality(item, QUALITY_CHANGE_STANDARD);
        }
        decreaseSellIn(item);
    }

    private void standardHandler(Item item) {
        decreaseQuality(item, QUALITY_CHANGE_STANDARD);
        if (item.sellIn < 1) {
            decreaseQuality(item, QUALITY_CHANGE_STANDARD);
        }
        decreaseSellIn(item);
    }

    private void sulfurasHandler(Item item) {
        item.quality = LEGENDARY_QUALITY;
    }

    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case "Aged Brie":
                    agedBrieHandler(item);
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    backstageHandler(item);
                    break;
                case "Sulfuras, Hand of Ragnaros":
                    sulfurasHandler(item);
                    break;
                case "Conjured Mana Cake":
                    conjuredHandler(item);
                    break;
                default:
                    standardHandler(item);
                    break;
            }
        }
    }
}
