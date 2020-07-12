package pay.jh.me.moneysprinkling.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
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