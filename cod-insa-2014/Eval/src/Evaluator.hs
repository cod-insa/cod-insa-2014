module Evaluator (
            Ia(..)
        ,   Fight(..)
        ,   ias
        ,   maps
        ,   times
        ,   duels
        )  where

import Control.Monad (join)
import System.Process

main :: IO ()
main = mapM_ getFight duels

getFight :: [Fight] -> IO ()
getFight xs = mapM_ e xs

e x = do
    s <- readProcess "ruby executor.rb" [mapfile x, show (time x)] ""
    print s
    return ()

-- (lines [(name . left) x, (name . right) x])

data Ia = Ia    {   name :: String
                } deriving (Show, Eq, Ord)

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

combine :: (Eq a, Eq b, Ord a,  Ord b) => (a -> a -> b -> c -> d) -> [a] -> [b] -> [c] -> [[d]]
combine c s u v = [ t w x | w <- s, x <- s, w /= x, w < x ]
    where t w x = join [ [ c w x y z, c x w y z ] | y <- u, z <- v ]

duels :: [[Fight]]
duels = combine Fight ias maps times
