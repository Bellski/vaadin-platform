package ru.vaadinp.place;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PlaceRequest {
    private final String nameToken;
    private final Map<String, String> params;


    public PlaceRequest() {
        this.nameToken = null;
        this.params = null;
    }

    private PlaceRequest(String nameToken, Map<String, String> params) {
        this.nameToken = nameToken;
        this.params = params;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlaceRequest) {
            PlaceRequest req = (PlaceRequest) obj;
            if (nameToken == null || req.nameToken == null) {
                return false;
            }
            if (!nameToken.equals(req.nameToken)) {
                return false;
            }

            if (params == null) {
                return req.params == null;
            } else {
                return params.equals(req.params);
            }
        }
        return false;
    }

    public String getNameToken() {
        return nameToken;
    }


    public String getParameter(String key, String defaultValue) {
        String value = null;

        if (params != null) {
            value = params.get(key);
        }

        if (value == null) {
            value = defaultValue;
        }
        return value;
    }


    public Set<String> getParameterNames() {
        if (params != null) {
            return params.keySet();
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public int hashCode() {
        if (nameToken == null) {
            throw new RuntimeException(
                    "Cannot compute hashcode of PlaceRequest with a null nameToken");
        }
        return 11 * (nameToken.hashCode() + (params == null ? 0 : params.hashCode()));
    }

    @Override
    public String toString() {
        return "PlaceRequest(nameToken=" + nameToken + ", params=" + params + ")";
    }


    public boolean hasSameNameToken(PlaceRequest other) {
        if (nameToken == null || other.nameToken == null) {
            return false;
        }
        return nameToken.equals(other.nameToken);
    }


    public boolean  matchesNameToken(String nameToken) {
        if (this.nameToken == null || nameToken == null) {
            return false;
        }
        return this.nameToken.equals(nameToken);
    }

    public static final class Builder {
        private String nameToken;
        private Map<String, String> params;


        public Builder() {
        }


        public Builder(PlaceRequest request) {
            nameToken = request.nameToken;
            with(request.params);
        }

        public Builder nameToken(String nameToken) {
            this.nameToken = nameToken;

            return this;
        }

        public Builder with(String name, String value) {
            lazyInitializeParamMap();
            if (value != null) {
                this.params.put(name, value);
            }

            return this;
        }

        public Builder with(Map<String, String> params) {
            if (params != null) {
                lazyInitializeParamMap();
                this.params.putAll(params);
            }

            return this;
        }

        public Builder without(String name) {
            if (params != null) {
                params.remove(name);
            }

            return this;
        }

        private void lazyInitializeParamMap() {
            if (this.params == null) {
                this.params = new LinkedHashMap<>();
            }
        }

        public PlaceRequest build() {
            return new PlaceRequest(nameToken, params);
        }
    }
}