package nc.unc.cs.services.communal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackgroundTaskService {

  /** Логгер. */
  private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskService.class);

  // 1. Извлекается первая попавшаяся недвижимость, с *доступной датой для налогооблажения;
  // 2. Берётся область полученного объекта;
  // 3. Происходит выборка недвижимости (20-50 шт) с *доступной датой и идентичной областью;
  // 4. Происходит налоговый расчёт и сохранение квитанций и новой налоговой даты в таблице Property;
  // 5. Переход к пункту 1;
  // ! В случае отсутствия прейскуранта для конвретной области,
  // расчёт происходит по стандартному прейскуранту
  // *доступная дата - дата превышающая налоговый период
  @Scheduled(fixedRate = 5000)
  public void reportDate() {
    LOGGER.info("WORKS!!!");
  }
}
