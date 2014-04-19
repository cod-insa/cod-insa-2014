module Evaluator (
            Ia(..)
        ,   Fight(..)
        ,   ias
        ,   combine
        ,   duels
        )  where

data Ia = Ia    {   name :: String
                } deriving (Show, Eq)

data Fight = Fight  {   left :: Ia
                    ,   right :: Ia
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

combine :: (Eq a, Eq b) => (a -> a -> b) -> [a] -> [b]
combine c s  = [ c x y | x <- s, y <- s, x /= y ]

duels :: [Fight]
duels = combine Fight ias
