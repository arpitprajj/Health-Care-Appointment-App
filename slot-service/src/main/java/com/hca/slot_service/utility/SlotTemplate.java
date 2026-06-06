package com.hca.slot_service.utility;

import java.time.LocalTime;
import java.util.List;

public class SlotTemplate {

    public static final List<LocalTime[]> DAILY_SLOTS =
            List.of(
                    new LocalTime[]{
                            LocalTime.of(10,0),
                            LocalTime.of(11,0)
                    },
                    new LocalTime[]{
                            LocalTime.of(11,0),
                            LocalTime.of(12,0)
                    },
                    new LocalTime[]{
                            LocalTime.of(14,0),
                            LocalTime.of(15,0)
                    },
                    new LocalTime[]{
                            LocalTime.of(15,0),
                            LocalTime.of(16,0)
                    },
                    new LocalTime[]{
                            LocalTime.of(17,0),
                            LocalTime.of(18,0)
                    }
            );

}