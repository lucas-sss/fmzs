package cn.onearth.fmzs.spider.proxy;

/**
 * 代理信息
 */
public class Proxy {
    /**  */
    private static final long serialVersionUID = 3170076121519327287L;
    /** 是否是ADSL代理 **/
    private boolean           isAdsl           = true;
    /** 代理所在城市 **/
    private String cityName;
    /** 代理IP或者域名 */
    private String host;
    /** 代理端口 */
    private int               port;
    /** 账号 */
    private String username;
    /** 密码 */
    private String password;
    /** 代理类型 */
    private String proxyType;
    /** adsl拨号之后的ip */
    private String adslIp;


    public Proxy() {
    }

    public Proxy(boolean isAdsl, String cityName, String host, int port, String username, String password, String proxyType) {
        super();
        this.isAdsl = isAdsl;
        this.cityName = cityName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.proxyType = proxyType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdsl() {
        return isAdsl;
    }

    public void setIsAdsl(boolean isAdsl) {
        this.isAdsl = isAdsl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getAdslIp() { return adslIp; }

    public void setAdslIp(String adslIp) { this.adslIp = adslIp; }
}
