# common-logs
common-logs logutil logbetter
#eg
private final static Logger logger = LoggerFactory.getLogger(XX.class);

LogBetter.instance(logger)
                .setLevel(LogLevel.INFO)
                .setMsg("xx start")
                .addParam("参数", "param")
                .log();
try{


} catch (Exception e) {
    LogBetter.instance(logger)
            .setLevel(LogLevel.ERROR)
            .setErrorMsg("xx异常")
            .setException(e)
            .log();
}
