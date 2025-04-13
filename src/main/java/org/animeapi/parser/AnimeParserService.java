package org.animeapi.parser;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.animeapi.model.Anime;
import org.animeapi.repository.AnimeRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimeParserService {

    private final AnimeRepository animeRepository;

    public void parseAndSaveTopAnime(int count) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new");

        WebDriver mainDriver = new ChromeDriver(options);

        try {
            String url = "https://shikimori.one/animes";
            log.info("🌐 Открываем страницу: {}", url);
            mainDriver.get(url);

            WebDriverWait wait = new WebDriverWait(mainDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("article.c-column.b-catalog_entry")));

            List<WebElement> animeCards = mainDriver.findElements(By.cssSelector("article.c-column.b-catalog_entry"));
            log.info("🔍 Найдено {} карточек", animeCards.size());

            int parsed = 0;

            for (WebElement card : animeCards) {
                if (parsed >= count) break;

                try {
                    String animePageUrl = card.findElement(By.cssSelector("a.cover")).getAttribute("href");

                    WebDriver detailDriver = new ChromeDriver(options);
                    detailDriver.get(animePageUrl);

                    WebDriverWait detailWait = new WebDriverWait(detailDriver, Duration.ofSeconds(10));
                    detailWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));

                    String name = detailDriver.findElement(By.cssSelector("h1")).getText();
                    String description = detailDriver.findElement(By.cssSelector(".b-text_with_paragraphs")).getText();
                    String ratingStr = detailDriver.findElement(By.cssSelector("meta[itemprop='ratingValue']")).getAttribute("content");
                    double rating = Double.parseDouble(ratingStr);
                    String photoUrl = detailDriver.findElement(By.cssSelector("meta[itemprop='image']")).getAttribute("content");

                    List<WebElement> lines = detailDriver.findElements(By.cssSelector("div.line-container"));
                    String genres = "Не указано";

                    for (WebElement line : lines) {
                        WebElement key = line.findElement(By.cssSelector("div.key"));
                        if (key.getText().contains("Жанры")) {
                            List<WebElement> genreTags = line.findElements(By.cssSelector("div.value a.b-tag"));
                            genres = genreTags.stream()
                                    .map(WebElement::getText)
                                    .collect(Collectors.joining(", "));
                            break;
                        }
                    }

                    Anime anime = new Anime();
                    anime.setAnimeName(name);
                    anime.setAnimeDescription(description);
                    anime.setAnimeRating((int) rating);
                    anime.setAnimeGenre(genres);
                    anime.setPhotoUrl(photoUrl);

                    animeRepository.save(anime);
                    log.info("✔ Сохранили аниме: {}", name);
                    parsed++;

                    detailDriver.quit();

                } catch (Exception e) {
                    log.warn("❌ Ошибка при парсинге карточки №{}: {}", parsed + 1, e.getMessage());
                }
            }

            log.info("✅ Всего успешно сохранено: {}", parsed);

        } catch (Exception e) {
            log.error("💥 Ошибка во время парсинга: ", e);
        } finally {
            mainDriver.quit();
            log.info("🧹 Главный драйвер закрыт");
        }
    }
}
