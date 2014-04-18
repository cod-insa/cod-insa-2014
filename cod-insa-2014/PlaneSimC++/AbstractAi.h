#ifndef ABSTRACTAI_H
#define ABSTRACTAI_H


class AbstractAi
{
    public:
        AbstractAi();
        virtual ~AbstractAi();

        /**
         * Must be implemented. This is the starting point of your AI.
         */
        virtual void think() = 0;

        /**
         * Called when the client has finish
         * Can occure when an error occure or the game ends
         * Use it specifically if you have files to close, else leave it empty
         */
        public void end();

    protected:
    private:
};

#endif // ABSTRACTAI_H
