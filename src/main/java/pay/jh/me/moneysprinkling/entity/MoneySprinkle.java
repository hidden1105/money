package pay.jh.me.moneysprinkling.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "money_sprinkle")
public class MoneySprinkle {
    @Id
    @Column(name = "token", length = 3, nullable = false)
    private String token;

    @NonNull
    private String roomId;

    @NonNull
    private String userId;

    @NonNull
    private double amount;

    @NonNull
    private int count;
}
