package org.animeapi.model;

import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Data
public class FavoriteId implements Serializable {
    private Long user;
    private Long anime;

    public FavoriteId() {}

    public FavoriteId(Long user, Long anime) {
        this.user = user;
        this.anime = anime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(anime, that.anime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, anime);
    }
}
