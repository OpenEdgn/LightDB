import edgn.lightdb.api.LightDB;
import edgn.lightdb.memory.MemoryLightDB;

module edgn.lightdb.memory {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires edgn.lightdb.api;
    requires edgn.lightdb.utils;
    opens edgn.lightdb.memory;
    exports edgn.lightdb.memory;
    provides LightDB with MemoryLightDB;
}