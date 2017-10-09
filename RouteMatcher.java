import java.util.*;

public class RouteMatcher {

    private Set<String> routeParts;

    public RouteMatcher(Set<String> routeParts) {
        this.routeParts = routeParts;
    }


    /**
     * Метод преобразует ури в Route
     *
     * Route - это обьект содержащий
     *  .Полный путь
     *  .Патерн. Патерн это внутренний уникальный путь. Доступен список известных частей uri, неизвестные заменяются на
     *  "?", что предположительно является PlaceHolder
     *  .Параметры PlaceHolders aaa/{placeholder} = user/bellski
     *  .Параметры uri ?a=b
     *
     * @param incomingPath входящий ури
     * @return
     */
    public RouteData match(String incomingPath) {
        List<String> placeHolderParameters = null;
        Map<String, String> params = null;

        final StringJoiner routePatternBuilder = new StringJoiner("/");

        final char[] routPartChars = incomingPath.toCharArray();

        int routePartStart = -1; // стартовый индекс куска uri. foor/bar (f = index 0, b = index 5)
        int startParametersIndex = -1; // стартовый индекс мараметров uri foo/bar?a=b (a = index 8)

        /*
            Бежим по uri пока не встретим знак ?. Это будет означать конец Uri и начало параметров
         */
        for (int i = 0; i < routPartChars.length; i++) {

            // Присваиваем начало куска uri
            if (routePartStart == -1) {
                routePartStart = i;
            }

            // Если следующий символ /,? или конец строки, значит мы нашли конец куска uri
            if (i == routPartChars.length -1 || routPartChars[i + 1] == '/' || routPartChars[i + 1] == '?') {

                // + 1 включаем последний символ
                final String routePart = incomingPath.substring(routePartStart, i + 1);

                //Если в списке есть такой кусок, то добавляем его в билдер, если нет добавляем ? и сохраняем значение PlaceHolder
                if (routeParts.contains(routePart)) {
                    routePatternBuilder.add(routePart);
                } else {
                    if (placeHolderParameters == null) {
                        placeHolderParameters = new ArrayList<>();
                    }

                    placeHolderParameters.add(routePart);
                    routePatternBuilder.add("?");
                }

                // Если следующий символ ?, сохраняем индекс начала параметров
                // +2 пропускаем два символа, что бы встать на начало ключа параметра
                if (routPartChars[i + 1] == '?') {
                    startParametersIndex = i + 2;
                    break;
                }

                // сбрасываем значение для поиска следующего
                routePartStart = -1;

                // перескакиваем через символ '/', '?'
                i++;
            }
        }

        // Если есть параметры
        if (startParametersIndex >= 0) {
            params = new HashMap<>();
            
            int keyStart = -1;
            int keyEnd = -1;

            for (int i = startParametersIndex; i < routPartChars.length; i++) {
                if (keyStart == -1) {
                    keyStart = i;
                }

                // если следующий символ '='
                if (i != routPartChars.length -1 && routPartChars[i + 1] == '=') {
                    keyEnd = i;

                    //Проскакиваем через следущий символ
                    i++;

                    continue;
                }

                //Если конец строки или символ '&' Сохраняем ключ значение.
                if (i == routPartChars.length -1 || routPartChars[i + 1] == '&' ) {
                    params.put(incomingPath.substring(keyStart, keyEnd + 1), incomingPath.substring(keyEnd + 2, i + 1));

                    keyStart = -1;
                    keyEnd = -1;

                    i++;
                }
            }
        }

        return new RouteData(incomingPath, routePatternBuilder.toString(), placeHolderParameters, params);
    }

    public class RouteData {
        
        private final String incomingPath;
        private final String routePattern;
        private final List<String> placeHolderParameters;
        private final Map<String, String> params;

        public RouteData(String incomingPath, String routePattern) {
            this(incomingPath, routePattern, null, null);
        }

        public RouteData(String incomingPath, String routePattern, List<String> placeHolderParameters) {
            this(incomingPath, routePattern, placeHolderParameters, null);
        }

        public RouteData(String incomingPath, String routePattern, Map<String, String> params) {
            this(incomingPath, routePattern, null, params);
        }

        public RouteData(String incomingPath, String routePattern, List<String> placeHolderParameters, Map<String, String> params) {
            this.incomingPath = incomingPath;
            this.routePattern = routePattern;
            this.placeHolderParameters = placeHolderParameters;
            this.params = params;
        }

        public String getIncomingPath() {
            return incomingPath;
        }

        public String getRoutePattern() {
            return routePattern;
        }

        public List<String> getPlaceHolderParameters() {
            return placeHolderParameters;
        }

        public Map<String, String> getParams() {
            return params;
        }

        @Override
        public String toString() {
            return "RouteData{" +
                    "incomingPath='" + incomingPath + '\'' +
                    ", routePattern='" + routePattern + '\'' +
                    ", placeHolderParameters=" + placeHolderParameters +
                    ", params=" + params +
                    '}';
        }
    }


    public static void main(String[] args) {
        final String uri = "user/bellski/settings?yy=zz";

        final Set<String> knownRouteParts = new HashSet<>();
        knownRouteParts.add("user");
        knownRouteParts.add("settings");

        final RouteData routeData = new RouteMatcher(knownRouteParts).match(uri);

        System.out.println(routeData);

        System.out.println(routeData.getIncomingPath().equals(uri));
        System.out.println(routeData.getRoutePattern().equals("user/?/settings"));
        System.out.println(routeData.getParams().size() == 1);
        System.out.println(routeData.getPlaceHolderParameters().size() == 1);

        System.out.println(routeData.getParams().get("yy").equals("zz"));
        System.out.println(routeData.getPlaceHolderParameters().get(0).equals("bellski"));
    }
}
