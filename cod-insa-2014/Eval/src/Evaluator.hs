module Evaluator (
            Ia(..)
        ,   Fight(..)
        ,   ias
        ,   maps
        ,   times
        ,   duels
        ,   aggregate
        )  where

data Ia = Ia    {   name :: String
                } deriving (Show, Eq)

data Fight = Fight  {   left    :: Ia
                    ,   right   :: Ia
                    ,   mapfile :: String
                    ,   time    :: Int
                    } deriving (Show, Eq)

ias :: [Ia]
ias = [
        Ia "Lyon",
        Ia "Rennes",
        Ia "Strasbourg",
        Ia "Toulouse",
        Ia "Rouen",
        Ia "Centre Val de Loire"
      ]

maps :: [String]
maps = ["france"]

times :: [Int]
times = [100, 1000, 3600]

combine :: (Eq a, Eq b) => (a -> a -> b -> c -> d) -> [a] -> [b] -> [c] -> [d]
combine c s u v = [ c w x y z | w <- s, x <- s, y <- u, z <- v, w /= x ]

duels :: [Fight]
duels = combine Fight ias maps times

aggregate :: [Fight] -> [[Fight]]
aggregate = aggregate' []
    where aggregate' acc [] = acc
          aggregate' acc (x:xs) = aggregate' (caughted:acc) lefted
            where caughted = filter (eqSwitch x) xs
                  lefted = filter (not . eqSwitch x) xs
                  eqSwitch a b = (left a == right b) && (right a == left b)
