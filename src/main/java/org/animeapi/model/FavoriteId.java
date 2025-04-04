// FavoriteId for Composite Key
package org.animeapi.model;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteId implements Serializable {
    private Integer user;
    private Integer anime;
}
