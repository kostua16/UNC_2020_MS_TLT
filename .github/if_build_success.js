module.exports = async ({ github, context }) => {
    if (context.issue.number) {
        const { data: pull } = await github.pulls.get({
            pull_number: context.issue.number,
            owner: context.repo.owner,
            repo: context.repo.repo,
        });
        if (pull && pull.number) {
            try {
                await github.issues.addLabels({
                    issue_number: pull.number,
                    owner: context.repo.owner,
                    repo: context.repo.repo,
                    labels: ["build_passed"],
                });
            } catch (except) {
                console.log(except);
            }
            try {
                await github.issues.removeLabel({
                    issue_number: pull.number,
                    owner: context.repo.owner,
                    repo: context.repo.repo,
                    name: "build_failed",
                });
            } catch (except) {
                console.log(except);
            }
            await github.issues.createComment({
                issue_number: pull.number,
                owner: context.repo.owner,
                repo: context.repo.repo,
                body: `Build successfully completed for commit ${context.sha}`,
            });
        }
    }

    return null;
};
