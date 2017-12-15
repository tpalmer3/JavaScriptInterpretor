redis = redis_d;

redis.append                    = redis_fix.append;
redis.clusterCountKeysInSlot    = redis_fix.clusterCountKeysInSlot;
redis.clusterKeySlot            = redis_fix.clusterKeySlot;
redis.decr                      = redis_fix.decr;
redis.decr_byte                 = redis_fix.decr_byte;
redis.decrBy                    = redis_fix.decrBy;
redis.decrBy_byte               = redis_fix.decrBy_byte;
redis.eval                      = redis_fix.eval;
redis.evalsha                   = redis_fix.evalsha;
redis.expire                    = redis_fix.expire;
redis.expire_byte               = redis_fix.expire_byte;
redis.ttl                       = redis_fix.ttl;

lua = redis.eval;

console.log("Redis Setup Complete");