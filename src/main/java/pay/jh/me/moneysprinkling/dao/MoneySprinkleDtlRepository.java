package pay.jh.me.moneysprinkling.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pay.jh.me.moneysprinkling.model.MoneySprinkleDtl;

import java.util.Optional;

public interface MoneySprinkleDtlRepository extends JpaRepository<MoneySprinkleDtl, String> {

    MoneySprinkleDtl findTop1ByToken(String token);
}
