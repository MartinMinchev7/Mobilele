package bg.sofuni.mobilele.web.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("@annotation(WarnIfExecutionExceed)")
    void onWarnIfExecutionTimeExceeds(){}
}
