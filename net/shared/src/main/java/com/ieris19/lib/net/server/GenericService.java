package com.ieris19.lib.net.server;

import java.io.Closeable;
import java.io.Serializable;
import java.rmi.Remote;

public interface GenericService extends Remote, Closeable, Serializable {
}
