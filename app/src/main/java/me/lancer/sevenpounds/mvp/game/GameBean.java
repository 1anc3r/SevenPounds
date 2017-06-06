package me.lancer.sevenpounds.mvp.game;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class GameBean {

    private int id;
    private int intType;
    private String strType;
    private String name;
    private String description;
    private String requirements;
    private String supportedLanguages;
    private String developers;
    private String publishers;
    private int score;
    private boolean discounted;
    private int discountPercent;
    private int originalPrice;
    private int finalPrice;
    private String currency;
    private String largeCapsuleImage;
    private String smallCapsuleImage;
    private String headerImage;
    private String background;
    private boolean windowsAvailable;
    private boolean macAvailable;
    private boolean linuxAvailable;
    private List<String> screenshots;

    public GameBean() {
    }

    public GameBean(int type, String name) {
        this.intType = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLargeCapsuleImage() {
        return largeCapsuleImage;
    }

    public void setLargeCapsuleImage(String largeCapsuleImage) {
        this.largeCapsuleImage = largeCapsuleImage;
    }

    public String getSmallCapsuleImage() {
        return smallCapsuleImage;
    }

    public void setSmallCapsuleImage(String smallCapsuleImage) {
        this.smallCapsuleImage = smallCapsuleImage;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public boolean isWindowsAvailable() {
        return windowsAvailable;
    }

    public void setWindowsAvailable(boolean windowsAvailable) {
        this.windowsAvailable = windowsAvailable;
    }

    public boolean isMacAvailable() {
        return macAvailable;
    }

    public void setMacAvailable(boolean macAvailable) {
        this.macAvailable = macAvailable;
    }

    public boolean isLinuxAvailable() {
        return linuxAvailable;
    }

    public void setLinuxAvailable(boolean linuxAvailable) {
        this.linuxAvailable = linuxAvailable;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
